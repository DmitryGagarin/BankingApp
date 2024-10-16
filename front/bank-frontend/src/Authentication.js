import React, { useState } from 'react';

// Component called Registration. 
// Can be considered as a class with methods inside 
const Authentication = () => {
    const [formData, setFormData] = useState({
        // names of input fields
        // it creates object formData with these fields
        email: '',     // formData.email
        password: '',  // formData.password
    });

    // creates object "errors" that will be filled with fields
    const [errors, setErrors] = useState({});

    const handleChange = (e) => {
        setFormData({ ...formData, [e.target.name]: e.target.value });
    };

    // validation form (basicaly cheking if empty)
    const validateForm = () => {
        const newErrors = {};
        if (!formData.email) newErrors.email = 'Email is required.';
        if (!formData.password) newErrors.password = 'Password is required.';

        setErrors(newErrors);
        return Object.keys(newErrors).length === 0;
    };

    // handles submit button action
    const handleSubmit = (e) => {
        e.preventDefault();
        if (validateForm()) { // if everything is okay, just fill values into url
            console.log('Registration Successful', formData);
            fetch(`http://localhost:8081/login/${formData.email}/${formData.password}`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify(formData),
            })
                // error handling connected to network
                .then(response => {
                    if (!response.ok) {
                        throw new Error('Network response was not ok');
                    }
                    return response.text(); // Use .text() to get raw response
                })
                .then(text => {
                    console.log('Response text:', text); // Log the raw response text
                    try {
                        const data = JSON.parse(text); // Attempt to parse the text to JSON
                        alert("everything ok");
                        console.log('auth Successful:', data);
                    } catch (parseError) {
                        console.error('JSON Parse error:', parseError);
                        alert("Received unexpected response from the server.");
                    }
                })
                // if data is exist, we can move to another screen
                .then(data => {
                    alert("everything ok")
                    console.log('Auth Successful:', data);
                    // Redirect or update UI after successful registration
                })
                // if no connection to backend
                .catch((error) => {
                    console.error('Error during auth:', error);
                    alert("No connection to backend");
                });
        }
    };

    // return statement - what will be drawn after calling this component
    return (
        <>
            <h1>Login Page</h1>

            <form onSubmit={handleSubmit}>
                <div>
                    <label>Email:</label>
                    <input
                        type="email"
                        name="email"
                        value={formData.email}
                        onChange={handleChange}
                    />
                    {errors.email && <p>{errors.email}</p>}
                </div>

                <div>
                    <label>Password:</label>
                    <input
                        type="password"
                        name="password"
                        value={formData.password}
                        onChange={handleChange}
                    />
                    {errors.password && <p>{errors.password}</p>}
                </div>

                <button type="submit">Login</button>
            </form>
        </>
    );
};

export default Authentication;