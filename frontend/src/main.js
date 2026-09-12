import { homePage } from './pages/Home.js';

// Load Home.css as a real stylesheet (no bundler here to handle CSS imports)
const homeStyles = document.createElement('link');
homeStyles.rel = 'stylesheet';
homeStyles.href = './src/pages/Home.css';
document.head.appendChild(homeStyles);
//mvn spring-boot:run "-Dspring-boot.run.arguments=--server.port=8081"

import { compareColleges, createReview, getCollege, getColleges, getCourses, getRating, getReviews, predictColleges } from './api.js';

const app = document.querySelector('#app');
const state = { selected: new Set(), lastPage: null, detail: null };

function escapeHtml(value = '') {
  return String(value).replace(/[&<>'"]/g, (char) => ({ '&': '&amp;', '<': '&lt;', '>': '&gt;', "'": '&#39;', '"': '&quot;' }[char]));
}

function shell(content, active = 'home') {
  app.innerHTML = `
    <header class="topbar">
      <a class="brand" href="#/">Campus<span>Compass</span></a>

      <nav>
        <a class="nav-link ${active === 'home' ? 'active' : ''}" href="#/">Home</a>
        <a class="nav-link ${active === 'discover' ? 'active' : ''}" href="#/discover">
          Discover
        </a>
        <a class="nav-link ${active === 'compare' ? 'active' : ''}" href="#/compare">
          Compare <b id="compare-count">${state.selected.size}</b>
        </a>
      </nav>
    </header>

    <main class="page">
      ${content}
    </main>

    <footer>
      <div>
        <strong>CampusCompass</strong>
        <p>Find the place where your next chapter begins.</p>
      </div>
      <div class="footer-links">
        <a href="#/">Home</a>
        <a href="#/discover">Discover</a>
        <a href="#/compare">Compare</a>
        <a href="#/predictor">College Predictor</a>
      </div>
    </footer>
  `;
}
function loading(title = 'Loading') { return `<div class="state-card"><div class="spinner"></div><p>${title}…</p></div>`; }
function errorState(error) { return `<div class="state-card error"><strong>Something went wrong</strong><p>${escapeHtml(error.message || error)}</p><button class="button secondary" data-action="retry">Try again</button></div>`; }
function emptyState(text) { return `<div class="state-card"><h3>No colleges found</h3><p>${escapeHtml(text)}</p></div>`; }
function stars(rating) { return rating == null ? '<span class="muted">Not rated yet</span>' : `<span class="stars">★</span> ${Number(rating).toFixed(1)}`; }

function filtersFromForm(form) {
  const data = new FormData(form);
  return { search: data.get('search'), city: data.get('city'), state: data.get('state'), collegeType: data.get('collegeType'),
    minRating: data.get('minRating'), maxRating: data.get('maxRating'), minFees: data.get('minFees'), maxFees: data.get('maxFees'),
    sort: data.get('sort'), direction: data.get('direction'), page: 0, size: 12 };
}

async function renderDiscover(params = { page: 0, size: 12, sort: 'name', direction: 'asc' }) {
  shell(`<section class="hero"><p class="eyebrow">COLLEGE DISCOVERY</p><h1>Find a college that fits your future.</h1><p>Search verified college information, compare your shortlist, and hear from students.</p></section>
    <section class="panel filter-panel"><form id="search-form"><div class="search-row"><input name="search" value="${escapeHtml(params.search || '')}" placeholder="Search by college, city, state, or course" /><button class="button" type="submit">Search</button></div>
      <div class="filter-grid"><input name="city" value="${escapeHtml(params.city || '')}" placeholder="City" /><input name="state" value="${escapeHtml(params.state || '')}" placeholder="State" /><input name="collegeType" value="${escapeHtml(params.collegeType || '')}" placeholder="College type" /><input name="minRating" value="${escapeHtml(params.minRating || '')}" type="number" min="0" max="5" step="0.1" placeholder="Min rating" /><input name="minFees" value="${escapeHtml(params.minFees || '')}" type="number" min="0" step="0.01" placeholder="Min fees" /><input name="maxFees" value="${escapeHtml(params.maxFees || '')}" type="number" min="0" step="0.01" placeholder="Max fees" /><select name="sort"><option value="name" ${params.sort === 'name' ? 'selected' : ''}>Name</option><option value="rating" ${params.sort === 'rating' ? 'selected' : ''}>Rating</option><option value="establishedYear" ${params.sort === 'establishedYear' ? 'selected' : ''}>Established year</option></select><select name="direction"><option value="asc" ${params.direction === 'asc' ? 'selected' : ''}>Ascending</option><option value="desc" ${params.direction === 'desc' ? 'selected' : ''}>Descending</option></select></div></form></section>
    <section id="results" class="results">${loading('Finding colleges')}</section>`, 'discover');
  document.querySelector('#search-form').addEventListener('submit', (event) => { event.preventDefault(); renderDiscover(filtersFromForm(event.target)); });
  try {
    state.lastPage = await getColleges(params);
    renderResults(params);
  } catch (error) { document.querySelector('#results').innerHTML = errorState(error); }
}

function renderResults(params) {
  const page = state.lastPage;
  const colleges = page?.content || [];
  const results = document.querySelector('#results');
  if (!colleges.length) { results.innerHTML = emptyState('Try a broader search or remove a filter.'); return; }
  results.innerHTML = `<div class="results-head"><div><p class="eyebrow">${page.totalElements || colleges.length} RESULTS</p><h2>Explore your options</h2></div><button class="button ${state.selected.size ? '' : 'disabled'}" data-action="compare">Compare selected (${state.selected.size}/4)</button></div>
    <div class="card-grid">${colleges.map(collegeCard).join('')}</div>${pagination(page, params)}`;
  results.querySelectorAll('[data-college]').forEach((card) => card.addEventListener('click', (event) => { if (event.target.type !== 'checkbox') location.hash = `#/college/${card.dataset.college}`; }));
  results.querySelectorAll('[data-select]').forEach((input) => input.addEventListener('change', () => { if (input.checked && state.selected.size >= 4) { input.checked = false; alert('You can compare up to 4 colleges.'); return; } input.checked ? state.selected.add(Number(input.value)) : state.selected.delete(Number(input.value)); renderResults(params); }));
  results.querySelector('[data-action="compare"]')?.addEventListener('click', () => { if (state.selected.size) location.hash = '#/compare'; });
  results.querySelectorAll('[data-page]').forEach((button) => button.addEventListener('click', () => renderDiscover({ ...params, page: Number(button.dataset.page) })));
}

function collegeCard(college) {
  return `<article class="college-card" data-college="${college.id}"><div class="card-top"><label class="check"><input type="checkbox" data-select value="${college.id}" ${state.selected.has(college.id) ? 'checked' : ''}/> Compare</label><span class="rating">${stars(college.overallRating)}</span></div><h3>${escapeHtml(college.name)}</h3><p class="location">${escapeHtml([college.city, college.state].filter(Boolean).join(', '))}</p><p>${escapeHtml(college.description || 'Explore courses, fees, and student reviews.')}</p><div class="card-meta"><span>${escapeHtml(college.collegeType || college.ownershipType || 'College')}</span>${college.nirfRank ? `<span>NIRF #${college.nirfRank}</span>` : ''}</div><span class="text-link">View details →</span></article>`;
}

function pagination(page, params) {
  if (!page || page.totalPages <= 1) return '';
  return `<div class="pagination"><button class="button secondary" data-page="${page.number - 1}" ${page.first ? 'disabled' : ''}>← Previous</button><span>Page ${page.number + 1} of ${page.totalPages}</span><button class="button secondary" data-page="${page.number + 1}" ${page.last ? 'disabled' : ''}>Next →</button></div>`;
}

async function renderDetail(id) {
  shell(loading('Loading college'), 'discover');
  try {
    const [college, courses, reviews, rating] = await Promise.all([getCollege(id), getCourses(id), getReviews(id), getRating(id)]);
    state.detail = { college, courses, reviews, rating };
    shell(`<a class="back-link" href="#/">← Back to discovery</a><section class="detail-hero"><div><p class="eyebrow">COLLEGE PROFILE</p><h1>${escapeHtml(college.name)}</h1><p class="location">${escapeHtml([college.city, college.state, college.country].filter(Boolean).join(', '))}</p></div><div class="big-rating"><span class="stars">★</span><strong>${rating.averageRating == null ? '—' : Number(rating.averageRating).toFixed(1)}</strong><small>average rating</small></div></section><div class="detail-grid"><section class="panel"><h2>About</h2><p>${escapeHtml(college.description || 'No description available yet.')}</p><dl class="facts"><div><dt>Type</dt><dd>${escapeHtml(college.collegeType || '—')}</dd></div><div><dt>Ownership</dt><dd>${escapeHtml(college.ownershipType || '—')}</dd></div><div><dt>Established</dt><dd>${college.establishedYear || '—'}</dd></div><div><dt>Accreditation</dt><dd>${escapeHtml(college.accreditation || '—')}</dd></div></dl></section><section class="panel"><h2>Courses</h2>${courses.length ? `<div class="course-list">${courses.map(course => `<div class="course"><div><h3>${escapeHtml(course.name)}</h3><p>${escapeHtml([course.degreeLevel, course.specialization].filter(Boolean).join(' · '))}</p></div><strong>${course.totalFees == null ? 'Fees not listed' : `${course.currency || 'INR'} ${Number(course.totalFees).toLocaleString()}`}</strong></div>`).join('')}</div>` : '<p class="muted">No courses listed yet.</p>'}</section></div><section class="reviews-section"><div class="section-heading"><div><p class="eyebrow">STUDENT VOICES</p><h2>Reviews</h2></div><span class="review-count">${reviews.length} review${reviews.length === 1 ? '' : 's'}</span></div><div class="review-layout"><div id="review-list">${reviews.length ? reviews.map(reviewCard).join('') : '<div class="state-card"><p>No reviews yet. Be the first to share your experience.</p></div>'}</div><form id="review-form" class="panel review-form"><h3>Share your experience</h3><label>Name<input name="reviewerName" required maxlength="120" placeholder="Your name" /></label><label>Rating<select name="rating" required><option value="">Choose a rating</option><option>5</option><option>4</option><option>3</option><option>2</option><option>1</option></select></label><label>Title<input name="title" maxlength="255" placeholder="What stood out?" /></label><label>Review<textarea name="content" required placeholder="Tell future students about this college"></textarea></label><button class="button" type="submit">Post review</button><p id="review-message" class="form-message"></p></form></div></section>`, 'discover');
    document.querySelector('#review-form').addEventListener('submit', (event) => submitReview(event, id));
  } catch (error) { shell(`${errorState(error)}`, 'discover'); }
}

function reviewCard(review) { return `<article class="review"><div class="review-head"><strong>${escapeHtml(review.reviewerName)}</strong><span class="rating">★ ${Number(review.rating).toFixed(1)}</span></div>${review.title ? `<h3>${escapeHtml(review.title)}</h3>` : ''}<p>${escapeHtml(review.content)}</p><small>${review.reviewedAt || ''}</small></article>`; }

async function submitReview(event, id) {
  event.preventDefault(); const form = event.target; const message = document.querySelector('#review-message'); const button = form.querySelector('button'); button.disabled = true; message.textContent = 'Posting…';
  try { await createReview(id, Object.fromEntries(new FormData(form))); message.textContent = 'Review posted. Thank you!'; form.reset(); } catch (error) { message.textContent = error.message; message.className = 'form-message error-text'; } finally { button.disabled = false; }
}
function renderPredictor() {
  shell(`
    <section class="predictor-hero">
      <p class="eyebrow">SMART COLLEGE PREDICTOR</p>

      <h1>Find colleges that match your rank.</h1>

      <p>
        Enter your entrance exam, rank and preferences to discover
        colleges where you may have good, moderate or ambitious chances.
      </p>
    </section>

    <section class="predictor-section">

      <form id="predictor-form" class="predictor-form">

        <div class="form-group">
          <label>Entrance Exam</label>
          <select name="exam" required>
            <option value="">Select exam</option>
            <option value="JEE Main">JEE Main</option>
            <option value="MHT CET">MHT CET</option>
            <option value="NEET">NEET</option>
            <option value="BITSAT">BITSAT</option>
          </select>
        </div>

        <div class="form-group">
          <label>Your Rank</label>
          <input
            type="number"
            name="rank"
            min="1"
            placeholder="e.g. 24500"
            required
          />
        </div>

        <div class="form-group">
          <label>Category</label>
          <select name="category">
            <option value="Open">Open</option>
            <option value="OBC">OBC</option>
            <option value="SC">SC</option>
            <option value="ST">ST</option>
          </select>
        </div>

        <div class="form-group">
          <label>Preferred Location</label>
          <select name="location">
            <option value="">Any location</option>
            <option value="Maharashtra">Maharashtra</option>
            <option value="Karnataka">Karnataka</option>
            <option value="Delhi">Delhi</option>
            <option value="Telangana">Telangana</option>
            <option value="Rajasthan">Rajasthan</option>
          </select>
        </div>

        <button class="button predictor-button" type="submit">
          🎯 Predict Colleges
        </button>

      </form>

      <div id="predictor-results"></div>

    </section>
  `, 'predictor');

  document
    .querySelector('#predictor-form')
    .addEventListener('submit', handlePredictor);
}
async function handlePredictor(event) {
  event.preventDefault();

  const form = event.target;
  const data = new FormData(form);

  const exam = data.get('exam');
  const rank = Number(data.get('rank'));
  const category = data.get('category');
  const location = data.get('location');

  if (!rank || rank <= 0) {
    return;
  }

  const results = document.querySelector('#predictor-results');
  results.innerHTML = loading('Finding colleges for your rank');

  try {
    const prediction = await predictColleges({ exam, rank, category, location });

    results.innerHTML = `
      <div class="prediction-header">
        <p class="eyebrow">PREDICTION RESULTS</p>
        <h2>Colleges for rank ${prediction.rank.toLocaleString()}</h2>
        <p>Based on <strong>${escapeHtml(prediction.exam)}</strong> and your current rank.</p>
      </div>

      ${predictionGroup('🟢', 'Good Chances', 'Colleges where your rank looks competitive.', prediction.goodChances)}
      ${predictionGroup('🟡', 'Moderate Chances', 'Colleges where admission may be possible.', prediction.moderateChances)}
      ${predictionGroup('🔴', 'Ambitious', 'More competitive colleges that may be difficult.', prediction.ambitious)}

      <div class="prediction-note">
        <strong>Important:</strong>
        This is a rule-based estimate for demonstration purposes.
        Actual admission depends on the year, category, counselling
        round, seat availability and official cutoffs.
      </div>
    `;
//    attachPredictionCardListeners(results);
  } catch (error) {
    results.innerHTML = errorState(error);
  }
}
function predictionGroup(icon, title, description, colleges) {
  return `
    <section class="prediction-group">

      <div class="prediction-group-heading">
        <div>
          <h2>${icon} ${title}</h2>
          <p>${description}</p>
        </div>

        <span>${colleges.length}</span>
      </div>

      ${
        colleges.length
          ? `
            <div class="prediction-card-grid">
              ${colleges.map(college => `
                <article
                  class="prediction-card"
                  data-prediction-college="${college.id}"
                >

                  <div class="prediction-card-top">
                    <div class="prediction-college-icon">
                      🎓
                    </div>

                    <div class="prediction-college-info">
                      <h3>${escapeHtml(college.name)}</h3>
                      <p>
                        📍 ${escapeHtml(college.city || 'Location not available')}
                      </p>
                    </div>
                  </div>

                  <div class="prediction-card-details">
                    <div class="prediction-detail">
                      <span>Estimated Cutoff</span>
                      <strong>
                        ${
                          college.cutoff != null
                            ? college.cutoff.toLocaleString()
                            : '—'
                        }
                      </strong>
                    </div>
                  </div>

                                    ${
                                      college.id != null
                                        ? `<a class="prediction-card-footer" href="#/college/${college.id}">
                                            <span>View college profile</span>
                                            <span class="prediction-arrow">→</span>
                                          </a>`
                                        : `<div class="prediction-card-footer prediction-card-footer-disabled">
                                            <span>Profile not linked yet</span>
                                          </div>`
                                    }

                </article>
              `).join('')}
            </div>
          `
          : `
            <div class="empty-prediction">
              No colleges in this category for the selected rank.
            </div>
          `
      }

    </section>
  `;
}
function attachPredictionCardListeners(container) {
  container
    .querySelectorAll('[data-prediction-college]')
    .forEach(card => {
      card.addEventListener('click', () => {
        const id = card.dataset.predictionCollege;
        if (id) {
          location.hash = `#/college/${id}`;
        }
      });
    });
}
async function renderCompare() {
  shell(`<a class="back-link" href="#/">← Back to discovery</a><section class="page-heading"><p class="eyebrow">SIDE BY SIDE</p><h1>Compare colleges</h1><p>Choose up to four colleges from discovery to compare their key details.</p></section><div id="compare-results">${state.selected.size ? loading('Building comparison') : emptyState('Select colleges from the discovery page first.')}</div>`, 'compare');
  if (!state.selected.size) return;
  try { const colleges = await compareColleges([...state.selected]); document.querySelector('#compare-results').innerHTML = `<div class="compare-grid">${colleges.map(c => `<article class="compare-card"><h2>${escapeHtml(c.name)}</h2><p class="location">${escapeHtml([c.city, c.state].filter(Boolean).join(', '))}</p><dl class="compare-facts"><div><dt>Rating</dt><dd>${stars(c.overallRating)}</dd></div><div><dt>Type</dt><dd>${escapeHtml(c.collegeType || '—')}</dd></div><div><dt>Ownership</dt><dd>${escapeHtml(c.ownershipType || '—')}</dd></div><div><dt>Established</dt><dd>${c.establishedYear || '—'}</dd></div><div><dt>NIRF rank</dt><dd>${c.nirfRank || '—'}</dd></div><div><dt>Reviews</dt><dd>${c.reviewCount ?? 0}</dd></div></dl><a class="text-link" href="#/college/${c.id}">Open profile →</a></article>`).join('')}</div>`; } catch (error) { document.querySelector('#compare-results').innerHTML = errorState(error); }
}
function renderHome() {
  shell(homePage(), 'home');

  const form = document.querySelector('#predictor-ui-form');
  if (form) form.addEventListener('submit', handleHomePredictor);
}

async function handleHomePredictor(event) {
  event.preventDefault();

  const form = event.target;
  const data = new FormData(form);

  const exam = data.get('exam');
  const rank = Number(data.get('rank'));
  const category = data.get('category');
  const location = data.get('location');

  const message = document.querySelector('#predictor-message');
  const results = document.querySelector('#home-predictor-results');

  if (!rank || rank <= 0) {
    message.textContent = 'Please enter a valid rank.';
    message.className = 'predictor-message error-text';
    return;
  }

  message.textContent = 'Finding colleges…';
  message.className = 'predictor-message';
  results.innerHTML = '';

  try {
    const prediction = await predictColleges({ exam, rank, category, location });

    message.textContent = '';
    results.innerHTML = `
      <div class="prediction-header">
        <p class="eyebrow">PREDICTION RESULTS</p>
        <h2>Colleges for rank ${prediction.rank.toLocaleString()}</h2>
        <p>Based on <strong>${escapeHtml(prediction.exam)}</strong> and your current rank.</p>
      </div>
      ${predictionGroup('🟢', 'Good Chances', 'Colleges where your rank looks competitive.', prediction.goodChances)}
      ${predictionGroup('🟡', 'Moderate Chances', 'Colleges where admission may be possible.', prediction.moderateChances)}
      ${predictionGroup('🔴', 'Ambitious', 'More competitive colleges that may be difficult.', prediction.ambitious)}
    `;
  } catch (error) {
    message.textContent = error.message;
    message.className = 'predictor-message error-text';
  }
}
function route() {
  const parts = location.hash.replace(/^#\/?/, '').split('/');

  if (parts[0] === 'college' && parts[1]) {
    renderDetail(parts[1]);
  } else if (parts[0] === 'compare') {
    renderCompare();
  } else if (parts[0] === 'discover') {
    renderDiscover();
  } else if (parts[0] === 'predictor') {
    renderPredictor();
  } else {
    renderHome();
  }
}
window.addEventListener('hashchange', route);
route();