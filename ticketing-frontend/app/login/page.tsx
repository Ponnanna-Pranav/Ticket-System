'use client';
import { useState } from 'react';
import { apiBase, setToken } from '../../lib/api';

export default function LoginPage(){
  const [email, setEmail] = useState('user@demo.com');
  const [password, setPassword] = useState('user123');
  const submit = async (e:any)=>{
    e.preventDefault();
    const res = await fetch(`${apiBase}/auth/login`, {method:'POST', headers:{'Content-Type':'application/json'}, body: JSON.stringify({email, password})});
    if(!res.ok){ alert('Login failed'); return; }
    const data = await res.json();
    setToken(data.accessToken);
    localStorage.setItem('user', JSON.stringify(data.user));
    window.location.href='/dashboard';
  };
  return (
    <form onSubmit={submit} style={{ display:'flex', flexDirection:'column', gap:8, maxWidth:360 }}>
      <h3>Login</h3>
      <input value={email} onChange={e=>setEmail(e.target.value)} placeholder='email' />
      <input type='password' value={password} onChange={e=>setPassword(e.target.value)} placeholder='password' />
      <button>Login</button>
    </form>
  );
}
