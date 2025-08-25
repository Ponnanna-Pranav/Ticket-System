'use client';
import { useState } from 'react';
import { api } from '../../../lib/api';

export default function NewTicket(){
  const [subject, setSubject] = useState('Sample issue');
  const [description, setDescription] = useState('Describe your problem...');
  const [priority, setPriority] = useState('MEDIUM');
  const submit = async (e:any)=>{
    e.preventDefault();
    await api('/tickets', { method:'POST', body: JSON.stringify({subject, description, priority}) });
    window.location.href='/dashboard';
  };
  return (
    <form onSubmit={submit} style={{ display:'flex', flexDirection:'column', gap:8, maxWidth:480 }}>
      <h3>Create Ticket</h3>
      <input value={subject} onChange={e=>setSubject(e.target.value)} placeholder='Subject' />
      <textarea value={description} onChange={e=>setDescription(e.target.value)} placeholder='Description' />
      <select value={priority} onChange={e=>setPriority(e.target.value)}>
        <option>LOW</option><option>MEDIUM</option><option>HIGH</option><option>URGENT</option>
      </select>
      <button>Create</button>
    </form>
  );
}
