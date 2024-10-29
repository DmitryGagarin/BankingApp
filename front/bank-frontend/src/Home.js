import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; 

const Home = () => {
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');
    const [deposits, setDeposits] = useState('');

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

    useEffect(() => {
      fetch('http://localhost:8083/home')
          .then(response => {
              if (!response.ok) {
                  throw new Error('Network response was not ok');
              }
              return response.json();
          })
          .then(data => {
              console.log(data); // Log the response
              setDeposits(data.deposits);
          })
          .catch(error => console.error('Error fetching data:', error));
  }, []);

    const leaveAccount = () => {
      navigate('/login'); 
    }

    const createDeposit = () => {
      navigate('/create_deposit')
    }

    const allDeposits = () => {
      navigate('/all_deposits')
    }

    return (
      <>
        <header style={{ backgroundColor: 'green', display: 'flex', justifyContent: 'space-between', alignItems: 'center', padding: '10px 20px' }}>
          <div style={{ display: 'flex', alignItems: 'center' }}>
            <img src="logo.png" alt="Logo" style={{ height: '50px', marginRight: '20px' }} />
          </div>
          
          <div style={{ display: 'flex', alignItems: 'center' }}>
            <h3 style={{ margin: '0 20px', color: 'white' }}>{name} {surname}</h3>
            <button onClick={leaveAccount} style={{ margin: '0 10px' }}>Leave Account</button>
            <button onClick={createDeposit} style={{ margin: '0 10px' }}>Create Deposit</button>
            <button onClick={allDeposits}>All Deposits</button>
          </div>
        </header>
      </>
    );
};

export default Home;
