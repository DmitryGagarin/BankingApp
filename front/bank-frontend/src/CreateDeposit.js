import React, { useEffect, useState } from 'react';
import { useNavigate } from 'react-router-dom'; 

const CreateDeposit = () => {
    const [id, setId] = useState('');
    const [type, setType] = useState('');

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
                setId(data.id);
            })
            .catch(error => console.error('Error fetching data:', error));
    }, []);

    const handleSubmit = (e) => {
        console.log(`Fetching: http://localhost:8083/create_deposit/${id}/${type}`);
        e.preventDefault();
        console.log('Creating Debit Card Attempt:', { id, type });
        fetch(`http://localhost:8083/create_deposit/${id}/${type}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
            body: JSON.stringify({ id, type }),
        })
        .then(response => {
            if (!response.ok) {
                throw new Error('Network response was not ok');
            }
            return response.text();
        })
        .then(text => {
            console.log('Response text:', text);
            try {
                // Attempt to parse the response as JSON
                const data = JSON.parse(text);
                alert("Debit Card Created");
                console.log('Created Debit Card Successful:', data);
                navigate('/home');                
            } catch (parseError) {
                // If parsing fails, handle it as a plain text response
                console.log('Received plain text response:', text);
                alert(text); // Alert the plain text message
                navigate('/home');                
            }
        })
        .catch((error) => {
            console.error('Error during creating deposit:', error);
            alert("No connection to backend");
        });
    };

    const handleCheckboxChange = (e) => {
        setType(e.target.value)
    }
 
    return (
        <>
            <div style={{display:'flex', justifyContent:'center', alignItems:'center', height:"50vh"}}>
                <label>
                        <input 
                            type="checkbox" 
                            value="DEBIT_CARD" 
                            checked={type === 'DEBIT_CARD'}
                            onChange={handleCheckboxChange}
                        />
                        Debit Card
                </label>
                <label>
                        <input 
                            type="checkbox" 
                            value="CREDIT_CARD" 
                            checked={type === 'CREDIT_CARD'}
                            onChange={handleCheckboxChange}
                        />
                        Credit Card
                </label>
                <label>
                        <input 
                            type="checkbox" 
                            value="DEPOSIT" 
                            checked={type === 'DEPOSIT'}
                            onChange={handleCheckboxChange}
                        />
                        Deposit Account
                </label>
            </div>
            <div style={{display:'flex', justifyContent:'center', alignItems:'center', height:"50vh"}}>
                <button onClick={handleSubmit}>Create New Debit Card</button>
            </div>
        </>
    )
}

export default CreateDeposit