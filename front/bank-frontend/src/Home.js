// Home.js
import React, { useEffect, useState } from 'react';
import Header from './Header'; // Adjust the path as needed
import { useNavigate } from 'react-router-dom'; // Import useNavigate


const Home = () => {
    const [name, setName] = useState('');
    const [surname, setSurname] = useState('');

    const navigate = useNavigate(); // Define navigate


    useEffect(() => {
        fetch('http://localhost:8081/home')
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                setName(data.name);
                setSurname(data.surname);
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    const leaveAccount = () => {
        navigate('/login');
    };

    const goToHome = () => {
      navigate('/home')
    }

    const createDeposit = () => {
        navigate('/create_deposit');
    };

    const allDeposits = () => {
        navigate('/all_deposits');
    };

    return (
        <>
            <Header 
                name={name} 
                surname={surname} 
                leaveAccount={leaveAccount} 
                goToHome={goToHome}
                createDeposit={createDeposit} 
                allDeposits={allDeposits} 
            />
            {/* Additional content for Home can go here */}
        </>
    );
};

export default Home;
