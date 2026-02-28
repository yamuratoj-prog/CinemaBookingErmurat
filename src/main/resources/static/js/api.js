/* ===== API Helper ===== */
const BASE = '';

const API = {
  async getCinemas()            { return _get('/api/cinemas'); },
  async getCinema(id)           { return _get(`/api/cinemas/${id}`); },
  async createCinema(data)      { return _post('/api/cinemas', data); },
  async updateCinema(id, data)  { return _put(`/api/cinemas/${id}`, data); },
  async deleteCinema(id)        { return _del(`/api/cinemas/${id}`); },
  async searchCinemas(city)     { return _get(`/api/cinemas/search?city=${encodeURIComponent(city)}`); },

  async getMovies()             { return _get('/api/movies'); },
  async getMovie(id)            { return _get(`/api/movies/${id}`); },
  async createMovie(data)       { return _post('/api/movies', data); },
  async updateMovie(id, data)   { return _put(`/api/movies/${id}`, data); },
  async deleteMovie(id)         { return _del(`/api/movies/${id}`); },
  async moviesByGenre(genre)    { return _get(`/api/movies/genre/${encodeURIComponent(genre)}`); },
  async moviesByCinema(cid)     { return _get(`/api/movies/cinema/${cid}`); },

  async getViewers()            { return _get('/api/viewers'); },
  async getViewer(id)           { return _get(`/api/viewers/${id}`); },
  async createViewer(data)      { return _post('/api/viewers', data); },
  async updateViewer(id, data)  { return _put(`/api/viewers/${id}`, data); },
  async deleteViewer(id)        { return _del(`/api/viewers/${id}`); },

  async getTickets()            { return _get('/api/tickets'); },
  async getTicket(id)           { return _get(`/api/tickets/${id}`); },
  async bookTicket(vid,mid,seat){ return _postRaw(`/api/tickets/book?viewerId=${vid}&movieId=${mid}&seatNumber=${encodeURIComponent(seat)}`); },
  async cancelTicket(id)        { return _putRaw(`/api/tickets/${id}/cancel`); },
  async ticketsByViewer(vid)    { return _get(`/api/tickets/viewer/${vid}`); },
  async ticketsByMovie(mid)     { return _get(`/api/tickets/movie/${mid}`); },
};

async function _get(url) {
  const r = await fetch(BASE + url);
  if (!r.ok) { const e = await r.json().catch(()=>({error:'Ошибка сервера'})); throw e; }
  return r.json();
}
async function _post(url, data) {
  const r = await fetch(BASE + url, { method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify(data) });
  if (!r.ok) { const e = await r.json().catch(()=>({error:'Ошибка'})); throw e; }
  return r.json();
}
async function _put(url, data) {
  const r = await fetch(BASE + url, { method:'PUT', headers:{'Content-Type':'application/json'}, body: JSON.stringify(data) });
  if (!r.ok) { const e = await r.json().catch(()=>({error:'Ошибка'})); throw e; }
  return r.json();
}
async function _postRaw(url) {
  const r = await fetch(BASE + url, { method:'POST' });
  if (!r.ok) { const e = await r.json().catch(()=>({error:'Ошибка'})); throw e; }
  return r.json();
}
async function _putRaw(url) {
  const r = await fetch(BASE + url, { method:'PUT' });
  if (!r.ok) { const e = await r.json().catch(()=>({error:'Ошибка'})); throw e; }
  return r.json();
}
async function _del(url) {
  const r = await fetch(BASE + url, { method:'DELETE' });
  if (!r.ok) { const e = await r.json().catch(()=>({error:'Ошибка'})); throw e; }
  return r.json();
}

/* ===== Toast Notifications ===== */
function showToast(msg, type='success') {
  let container = document.querySelector('.toast-container');
  if (!container) {
    container = document.createElement('div');
    container.className = 'toast-container';
    document.body.appendChild(container);
  }
  const t = document.createElement('div');
  t.className = `toast ${type}`;
  const icons = { success:'✅', error:'❌', warning:'⚠️' };
  t.innerHTML = `<strong>${icons[type]||''} </strong> ${msg}`;
  container.appendChild(t);
  setTimeout(() => { t.style.opacity='0'; t.style.transform='translateX(100%)'; t.style.transition='all 0.3s'; setTimeout(()=>t.remove(), 300); }, 3500);
}

/* ===== Modal helpers ===== */
function openModal(id)  { document.getElementById(id).classList.add('active'); }
function closeModal(id) { document.getElementById(id).classList.remove('active'); }

/* ===== Confirm dialog ===== */
function confirmAction(message) {
  return new Promise(resolve => {
    const overlay = document.createElement('div');
    overlay.className = 'modal-overlay active';
    overlay.innerHTML = `
      <div class="modal" style="max-width:400px">
        <div class="modal-header"><h3>⚠️ Подтверждение</h3></div>
        <p class="confirm-text">${message}</p>
        <div class="form-actions" style="margin-top:20px">
          <button class="btn btn-outline" id="confirm-no">Отмена</button>
          <button class="btn btn-danger" id="confirm-yes">Подтвердить</button>
        </div>
      </div>`;
    document.body.appendChild(overlay);
    overlay.querySelector('#confirm-yes').onclick = () => { overlay.remove(); resolve(true); };
    overlay.querySelector('#confirm-no').onclick  = () => { overlay.remove(); resolve(false); };
  });
}

/* ===== Genre emoji ===== */
function genreEmoji(g) {
  const m = {'Драма':'🎭','Научная фантастика':'🚀','Боевик':'💥','Триллер':'😱','Комедия':'😂','Ужасы':'👻','Мелодрама':'💕','Документальный':'📽️','Анимация':'🎨'};
  return m[g] || '🎬';
}

