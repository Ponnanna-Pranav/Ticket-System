'use client';
import { useEffect, useState } from 'react';
import { api, getUser } from '../../../lib/api';
import { useParams } from 'next/navigation';

export default function TicketDetail(){
  const params = useParams();
  const id = params?.id as string;
  const [t, setT] = useState<any>(null);
  const [comments, setComments] = useState<any[]>([]);
  const [body, setBody] = useState('');
  const [status, setStatus] = useState('OPEN');
  const [user, setUser] = useState<any>(null);

  useEffect(()=>{
    setUser(getUser());
    api(`/tickets/${id}`).then(setT);
    api(`/tickets/${id}/comments`).then(setComments);
  },[id]);

  const postComment = async ()=>{
    await api(`/tickets/${id}/comments`, { method:'POST', body: JSON.stringify({ body }) });
    setBody('');
    setComments(await api(`/tickets/${id}/comments`));
  };
  const changeStatus = async ()=>{
    await api(`/tickets/${id}/status`, { method:'PATCH', body: JSON.stringify({ status }) });
    setT(await api(`/tickets/${id}`));
  };

  if(!t) return <div>Loading...</div>;
  return (
    <div>
      <h3>{t.subject}</h3>
      <p>Status: {t.status} | Priority: {t.priority}</p>
      <p>{t.description}</p>

      <div style={{marginTop:16}}>
        <h4>Comments</h4>
        <ul>{comments.map(c => <li key={c.id}>{c.body}</li>)}</ul>
        <textarea value={body} onChange={e=>setBody(e.target.value)} placeholder='Add a comment' />
        <button onClick={postComment}>Post</button>
      </div>

      <div style={{marginTop:16}}>
        <h4>Change Status</h4>
        <select value={status} onChange={e=>setStatus(e.target.value)}>
          <option>OPEN</option><option>IN_PROGRESS</option><option>RESOLVED</option><option>CLOSED</option>
        </select>
        <button onClick={changeStatus}>Update</button>
      </div>
    </div>
  );
}
