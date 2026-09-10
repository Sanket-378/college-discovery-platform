const API_BASE_URL = (window.__COLLEGE_API_BASE_URL__ || 'http://localhost:8080').replace(/\/$/, '');

export async function apiRequest(path, options = {}) {
  const response = await fetch(`${API_BASE_URL}${path}`, {
    ...options,
    headers: { 'Content-Type': 'application/json', ...(options.headers || {}) }
  });
  const text = await response.text();
  let body = null;
  try { body = text ? JSON.parse(text) : null; } catch { body = { message: text }; }
  if (!response.ok) {
    throw new Error(body?.message || `Request failed (${response.status})`);
  }
  return body;
}

export function getColleges(params = {}) {
  const query = new URLSearchParams();
  Object.entries(params).forEach(([key, value]) => {
    if (value !== undefined && value !== null && value !== '') query.set(key, value);
  });
  return apiRequest(`/api/colleges?${query}`);
}

export const getCollege = (id) => apiRequest(`/api/colleges/${id}`);
export const getCourses = (id) => apiRequest(`/api/colleges/${id}/courses`);
export const compareColleges = (ids) => apiRequest(`/api/colleges/compare?ids=${ids.join(',')}`);
export const getReviews = (id) => apiRequest(`/api/colleges/${id}/reviews`);
export const getRating = (id) => apiRequest(`/api/colleges/${id}/rating`);
export const createReview = (id, review) => apiRequest(`/api/colleges/${id}/reviews`, {
  method: 'POST',
  body: JSON.stringify(review)
});
