import './App.css';
import React from "react";
import {Route, Routes} from "react-router-dom";
import Dashboard from "./components/dashboard/Dashboard";
import Homepage from "./components/homepage/Homepage";
import Login from "./components/Login/Login";
import PrivateRoute from "./PriviteRoute/PrivateRoute";
import ShopsView from "./ShopsView/ShopsView";


function App() {
    return (
        <Routes>
            <Route
                path="/dashboard"
                element={
                    <PrivateRoute>
                        <Dashboard/>
                    </PrivateRoute>
                }/>
            <Route
                path="/shops/:id"
                element={
                    <PrivateRoute>
                        <ShopsView/>
                    </PrivateRoute>
                }/>
            <Route path="/" element={<Homepage/>}></Route>
            <Route path="/login" element={<Login/>}></Route>
        </Routes>
    );
}

export default App;
