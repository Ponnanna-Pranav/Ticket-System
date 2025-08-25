export default function RootLayout({ children }: { children: React.ReactNode }) {
  return (
    <html lang="en">
      <body style={{ fontFamily: 'sans-serif', margin: 24 }}>
        <h2>🎟️ Ticketing System</h2>
        <div>{children}</div>
      </body>
    </html>
  );
}
