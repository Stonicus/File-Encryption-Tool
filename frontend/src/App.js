import React, { useState } from 'react';
import './App.css';

function App() {
  const [file, setFile] = useState(null);
  const [password, setPassword] = useState('');
  const [downloadData, setDownloadData] = useState(null);
  const [loading, setLoading] = useState(false);

  const backendUrl = 'http://localhost:8080/api/encryption';

  const handleFileChange = (e) => {
    setFile(e.target.files[0]);
    setDownloadData(null);
  };

  const handlePasswordChange = (e) => {
    setPassword(e.target.value);
  };

  const handleEncrypt = async () => {
    if (!file || !password) {
      alert('Please select a file and enter a password');
      return;
    }
    setLoading(true);
    setDownloadData(null);

    const formData = new FormData();
    formData.append('file', file);
    formData.append('password', password);

    try {
      const response = await fetch(`${backendUrl}/encrypt`, {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) throw new Error('Encryption failed');

      const blob = await response.blob();

      const disposition = response.headers.get('Content-Disposition');
      let filename = 'download';

      if (disposition && disposition.includes('filename=')) {
        filename = disposition.split('filename=')[1];
      }

      const url = URL.createObjectURL(blob);
      setDownloadData({ url, filename });

    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  };

  const handleDecrypt = async () => {
    if (!file || !password) {
      alert('Please select a file and enter a password');
      return;
    }
    setLoading(true);
    setDownloadData(null);

    const formData = new FormData();
    formData.append('file', file);
    formData.append('password', password);

    try {
      const response = await fetch(`${backendUrl}/decrypt`, {
        method: 'POST',
        body: formData,
      });

      if (!response.ok) throw new Error('Decryption failed');

      const blob = await response.blob();

      const disposition = response.headers.get('Content-Disposition');
      let filename = 'download';

      if (disposition && disposition.includes('filename=')) {
        filename = disposition.split('filename=')[1];
      }

      const url = URL.createObjectURL(blob);
      setDownloadData({ url, filename });

    } catch (err) {
      alert(err.message);
    } finally {
      setLoading(false);
    }
  };

  return (
    <div className="app-container">
      <h1>File Encryption Tool</h1>

      <div>
        <input
          className="file-input"
          type="file"
          onChange={handleFileChange}
        />
      </div>

      <div style={{ marginTop: 10 }}>
        <input
          className="text-input"
          type="password"
          placeholder="Enter password"
          value={password}
          onChange={handlePasswordChange}
        />
      </div>

      <div style={{ marginTop: 10 }}>
        <button
          className="primary-btn"
          onClick={handleEncrypt}
          disabled={loading}
        >
          {loading ? 'Encrypting...' : 'Encrypt'}
        </button>
        <button
          className="secondary-btn"
          onClick={handleDecrypt}
          disabled={loading || !file?.name.endsWith('.enc')}
        >
          {loading ? 'Decrypting...' : 'Decrypt'}
        </button>
      </div>

      {downloadData && (
        <div className="download-box">
          <a
            href={downloadData.url}
            download={downloadData.filename}
          >
            Download {downloadData.filename}
          </a>
        </div>
      )}
    </div>
  );
}

export default App;
