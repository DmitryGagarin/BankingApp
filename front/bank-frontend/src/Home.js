import React from "react";
import { useNavigate } from "react-router-dom";

const Home = () => {
  const navigate = useNavigate();

  return (
    <div style={{ textAlign: 'center', marginTop: '50px' }}>
      <h1>Welcome to Our App</h1>
      <button onClick={() => navigate('/registration')}>Go to Registration</button>
      <button onClick={() => navigate('/login')}>Go to Login</button>
    </div>
  );
};

export default Home;