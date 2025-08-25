'use client';
import { useEffect, useState } from 'react';
import { api, getUser } from '../../lib/api';
import Link from 'next/link';

export default function Dashboard(){
  const [tickets, setTickets] = useState<any[]>([]);
  const [user, setUser] = useState<any>(null);
  useEffect(()=>{
    setUser(getUser());
    api('/tickets?scope=mine').then(r=>setTickets(r.content)).catch(()=>{});
  },[]);
  return (
    <div>
      <h3>My Tickets</h3>
      <Link href='/tickets/new'>Create Ticket</Link>
      <ul>
        {tickets.map(t=> <li key={t.id}><Link href={`/tickets/${t.id}`}>{t.subject} — {t.status}</Link></li>)}
      </ul>
    </div>
  );
}
