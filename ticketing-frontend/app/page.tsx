import Link from 'next/link';
export default function Home() {
  return (
    <div style={{ display: 'flex', gap: 12 }}>
      <Link href='/login'>Login</Link>
      <Link href='/dashboard'>Dashboard</Link>
    </div>
  );
}
