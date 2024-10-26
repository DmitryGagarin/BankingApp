import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; 

const Home = () => {
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const navigate = useNavigate();

    useEffect(() => {
        fetch('http://localhost:8081/home')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data); // Log the response
                setName(data.name);
                setSurname(data.surname);
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    const leaveAccount = () => {
      navigate('/login'); 
    }

    return (
      <>
        <div style={{display:'flex', justifyContent:'center', alignItems:'center', height:"50vh"}}>
          <h2>Welcome</h2>
        </div>
        <div style={{display:'flex', justifyContent:'center', alignItems:'center'}}>
          <h3>{name} {surname}!</h3>
        </div>
        <div style={{display:'flex', justifyContent:'center', alignItems:'center'}}>
          <button onClick={leaveAccount}>Leve Account</button>
        </div>
      </>
    );
};

export default Home;
