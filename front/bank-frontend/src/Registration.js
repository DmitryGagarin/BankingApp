import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import './Registration.css';

// Component called Registration
const Registration = () => {
  const [formData, setFormData] = useState({
    firstName: '',
    lastName: '',
    email: '',
    password: '',
    confirmPassword: '',
  });

  const [errors, setErrors] = useState({});
  const navigate = useNavigate(); // Initialize useNavigate

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const validateForm = () => {
    const newErrors = {};
    if (!formData.firstName) newErrors.firstName = 'First name is required.';
    if (!formData.lastName) newErrors.lastName = 'Last name is required.';
    if (!formData.email) newErrors.email = 'Email is required.';
    if (!formData.password) newErrors.password = 'Password is required.';
    if (formData.password !== formData.confirmPassword)
      newErrors.confirmPassword = 'Passwords do not match.';

    setErrors(newErrors);
    return Object.keys(newErrors).length === 0;
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    if (validateForm()) {
        console.log('Registration Successful', formData);
        fetch(`http://localhost:8081/register/${formData.firstName}/${formData.lastName}/${formData.email}/${formData.password}`, {
            method: 'POST',
            headers: {
                'Accept': 'application/json',
                'Content-Type': 'application/json',
            },
        })
        .then(response => {
            if (!response.ok) {
                return response.text().then(text => {
                    throw new Error(text); // Use the response text as the error message
                });
            }
            return response.text();
        })
        .then(text => {
            console.log('Response text:', text);
            alert(text); // Show success message
            navigate('/login'); // Navigate after successful registration
        })
        .catch((error) => {
            console.error('Error during registration:', error);
            alert(error.message); // Show the error message from the backend
        });
    }
};


  const moveToLogin = () => {
    navigate("/login");
  }


  return (
    <>
      <main style={{ backgroundColor: "pink", display: "flex", justifyContent: "center", alignItems: "center", height: "100vh", flexDirection: "column" }}>
        <h1 style={{ marginBottom: "20px" }}>Registration Page</h1>
        <form style={{ display: "flex", flexDirection: "column", width: "300px" }} onSubmit={handleSubmit}>
          <div style={{ marginBottom: "15px" }}>
            <label>First Name: </label>
            <input
              type="text"
              name="firstName"
              value={formData.firstName}
              onChange={handleChange}
              style={{ borderRadius: "25px", padding: "10px", width: "100%" }}
            />
            {errors.firstName && <p className='error'>{errors.firstName}</p>}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label>Last Name:</label>
            <input
              type="text"
              name="lastName"
              value={formData.lastName}
              onChange={handleChange}
              style={{ borderRadius: "25px", padding: "10px", width: "100%" }}
            />
            {errors.lastName && <p className='error'>{errors.lastName}</p>}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label>Email:</label>
            <input
              type="email"
              name="email"
              value={formData.email}
              onChange={handleChange}
              style={{ borderRadius: "25px", padding: "10px", width: "100%" }}
            />
            {errors.email && <p className='error'>{errors.email}</p>}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label>Password:</label>
            <input
              type="password"
              name="password"
              value={formData.password}
              onChange={handleChange}
              style={{ borderRadius: "25px", padding: "10px", width: "100%" }}
            />
            {errors.password && <p className='error'>{errors.password}</p>}
          </div>

          <div style={{ marginBottom: "15px" }}>
            <label>Confirm Password:</label>
            <input
              type="password"
              name="confirmPassword"
              value={formData.confirmPassword}
              onChange={handleChange}
              style={{ borderRadius: "25px", padding: "10px", width: "100%" }}
            />
            {errors.confirmPassword && <p className='error'>{errors.confirmPassword}</p>}
          </div>

          <button type="submit" style={{ borderRadius: "25px", padding: "10px", marginTop: "10px" }}>Register</button>
        </form>
        <br></br>
        <div>
          <button onClick={moveToLogin} style={{ borderRadius: "25px", padding: "10px", marginTop: "10px" }}>Already have account</button>
        </div>
      </main>
    </>
  );
};

export default Registration;
