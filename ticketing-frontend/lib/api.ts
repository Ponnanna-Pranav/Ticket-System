export const apiBase = process.env.NEXT_PUBLIC_API_BASE as string;

export function setToken(token: string){ localStorage.setItem('accessToken', token); }
export function getToken(){ return typeof window==='undefined'? null : localStorage.getItem('accessToken'); }
export function getUser(){ try{ return JSON.parse(localStorage.getItem('user')||'null'); } catch{ return null; } }

export async function api(path: string, options: RequestInit = {}){
  const token = getToken();
  const headers: any = {'Content-Type':'application/json', ...(token? { Authorization: `Bearer ${token}` } : {})};
  const res = await fetch(`${apiBase}${path}`, { ...options, headers });
  if(!res.ok) throw new Error(await res.text());
  if(res.status===204) return null;
  return res.json();
}
