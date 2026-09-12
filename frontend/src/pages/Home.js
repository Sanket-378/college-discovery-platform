export function homePage() {
  return `
    <!-- HERO -->
    <section class="home-hero">
      <div class="hero-content">
        <span class="home-eyebrow">CAMPUSCOMPASS</span>

        <h1>
          Find the right college.
          <span>Build your future.</span>
        </h1>

        <p>
          Discover colleges across India, explore courses, compare your
          options, read student reviews, and find colleges that match
          your rank.
        </p>

        <div class="hero-actions">
          <a href="#/discover" class="home-btn home-btn-primary">
            Explore Colleges
          </a>

           <a href="#/predictor" class="home-btn home-btn-secondary">
            Try College Predictor
          </a>
        </div>

        <div class="hero-stats">
          <div>
            <strong>15+</strong>
            <span>Colleges</span>
          </div>

          <div>
            <strong>4</strong>
            <span>Smart Features</span>
          </div>

          <div>
            <strong>100%</strong>
            <span>Student Focused</span>
          </div>
        </div>
      </div>

      <div class="hero-visual">
        <div class="floating-card floating-card-top">
          <span class="floating-icon">🎓</span>
          <div>
            <strong>College Discovery</strong>
            <small>Find your perfect match</small>
          </div>
        </div>

        <div class="campus-card">
          <div class="campus-card-header">
            <span>Recommended</span>
            <span class="campus-rating">★ 4.8</span>
          </div>

          <h3>College of Engineering Pune</h3>

          <p>Shivajinagar, Pune, Maharashtra</p>

          <div class="campus-tags">
            <span>Engineering</span>
            <span>Public</span>
          </div>

          <div class="campus-card-footer">
            <span>Explore college</span>
            <span>→</span>
          </div>
        </div>

        <div class="floating-card floating-card-bottom">
          <span class="floating-icon">📊</span>
          <div>
            <strong>Compare Colleges</strong>
            <small>Make better decisions</small>
          </div>
        </div>
      </div>
    </section>

    <!-- FEATURES -->
    <section class="home-section features-section" id="features">
      <div class="section-intro">
        <span class="home-eyebrow">WHAT YOU CAN DO</span>
        <h2>Everything you need to choose your college.</h2>
        <p>
          CampusCompass brings important college information together
          in one simple platform.
        </p>
      </div>

      <div class="feature-grid">

        <article class="feature-card feature-card-highlight">
          <div class="feature-icon">🔎</div>

          <span class="feature-number">01</span>

          <h3>College Search</h3>

          <p>
            Search and filter colleges by name, city, state, type,
            rating and fees.
          </p>

          <a href="#/discover">Search colleges</a>
        </article>

        <article class="feature-card">
          <div class="feature-icon">📋</div>

          <span class="feature-number">02</span>

          <h3>College Details</h3>

          <p>
            Explore college profiles, courses, ratings, ownership,
            establishment details and student reviews.
          </p>

          <a href="#/discover">View colleges</a>
        </article>

        <article class="feature-card">
          <div class="feature-icon">⚖️</div>

          <span class="feature-number">03</span>

          <h3>Compare Colleges</h3>

          <p>
            Shortlist up to four colleges and compare their important
            information side by side.
          </p>

          <a href="#/discover">Start comparing</a>
        </article>

        <article class="feature-card predictor-feature" id="predictor">
          <div class="feature-icon">🎯</div>

          <span class="feature-number">04</span>

          <h3>College Predictor</h3>

          <p>
            Enter your exam, rank, category and preferred location to
            discover colleges where you may have a chance.
          </p>

          <button
            class="predictor-demo-button"
            type="button"
            onclick="document.getElementById('predictor-form').scrollIntoView({behavior:'smooth'})"
          >
            start Predictor
          </button>


        </article>

      </div>
    </section>

   <!-- PREDICTOR -->
   <section class="predictor-section" id="predictor-form">
     <div class="predictor-wrapper">

       <div class="predictor-copy">
         <span class="home-eyebrow">SMART COLLEGE PREDICTOR</span>

         <h2>
           Your rank.
           <span>Your possibilities.</span>
         </h2>

         <p>
           Tell us about your entrance exam and rank. CampusCompass
           will match you with colleges using transparent cutoff-based
           logic.
         </p>

         <div class="predictor-points">
           <div>
             <span>✓</span>
             <p>Transparent recommendations</p>
           </div>

           <div>
             <span>✓</span>
             <p>Good, Moderate & Ambitious categories</p>
           </div>

           <div>
             <span>✓</span>
             <p>Dataset-driven matching</p>
           </div>
         </div>
       </div>

       <div class="predictor-card">

         <div class="predictor-card-header">
           <span class="predictor-icon">🎯</span>

           <div>
             <h3>Find your colleges</h3>
             <p>Enter your admission details</p>
           </div>
         </div>

         <form id="predictor-ui-form">

           <div class="predictor-form-grid">

             <label>
               Exam
               <select name="exam">
                 <option>JEE Main</option>
                 <option>MHT-CET</option>
                 <option>NEET</option>
                 <option>CUET</option>
               </select>
             </label>

             <label>
               Rank
               <input
                 type="number"
                 name="rank"
                 placeholder="e.g. 24500"
                 min="1"
               />
             </label>

             <label>
               Category
               <select name="category">
                 <option>Open</option>
                 <option>OBC</option>
                 <option>SC</option>
                 <option>ST</option>
                 <option>EWS</option>
               </select>
             </label>

             <label>
               Preferred Location
               <select name="location">
                 <option>Maharashtra</option>
                 <option>Karnataka</option>
                 <option>Delhi</option>
                 <option>Telangana</option>
                 <option>Rajasthan</option>
                 <option>Any State</option>
               </select>
             </label>

           </div>

           <button class="predict-button" type="submit">
             Predict Colleges
           </button>

           <p id="predictor-message" class="predictor-message"></p>

         </form>

       </div>
     </div>

     <div id="home-predictor-results"></div>
   </section>

    <!-- HOW IT WORKS -->
    <section class="home-section how-section">
      <div class="section-intro">
        <span class="home-eyebrow">HOW IT WORKS</span>

        <h2>From search to shortlist in minutes.</h2>
      </div>

      <div class="steps-grid">

        <div class="step">
          <span>01</span>
          <div>
            <h3>Discover</h3>
            <p>Search colleges using filters that matter to you.</p>
          </div>
        </div>

        <div class="step">
          <span>02</span>
          <div>
            <h3>Explore</h3>
            <p>Open detailed college profiles and courses.</p>
          </div>
        </div>

        <div class="step">
          <span>03</span>
          <div>
            <h3>Compare</h3>
            <p>Shortlist up to four colleges side by side.</p>
          </div>
        </div>

        <div class="step">
          <span>04</span>
          <div>
            <h3>Predict</h3>
            <p>Use your rank to discover realistic options.</p>
          </div>
        </div>

      </div>
    </section>

    <!-- ABOUT -->
    <section class="about-section" id="about">
      <div class="about-content">

        <div>
          <span class="home-eyebrow">ABOUT CAMPUSCOMPASS</span>

          <h2>
            Choosing a college shouldn't
            feel complicated.
          </h2>
        </div>

        <div>
          <p>
            CampusCompass is a student-focused college discovery
            platform designed to make college research simpler,
            clearer and more useful.
          </p>

          <p>
            Instead of jumping between multiple websites, students can
            discover colleges, inspect their details, compare options
            and eventually predict suitable colleges from their rank.
          </p>

          <a href="#/discover" class="about-link">
            Start exploring colleges
          </a>
        </div>

      </div>
    </section>

    <!-- CTA -->
    <section class="final-cta">
      <span class="home-eyebrow">YOUR NEXT CHAPTER STARTS HERE</span>

      <h2>Ready to find your college?</h2>

      <p>
        Explore colleges and start building your shortlist today.
      </p>

      <a href="#/discover" class="home-btn home-btn-primary">
        Explore Colleges
      </a>
    </section>
  `;
}