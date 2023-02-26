import './App.css';
import React, {useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Routes} from "react-router-dom";
import Products from "./components/Products/Products";
import Homepage from "./components/homepage/Homepage";
import Login from "./components/Login/Login";
import PrivateRoute from "./PriviteRoute/PrivateRoute";
import Shops from "./components/dashboard/Shops";
import ShopsView from "./ShopsView/ShopsView";
import {useLocalState} from "./util/useLocalStorage";
import jwt_decode from 'jwt-decode'
import ModeratorShops from "./components/ModeratorShops/ModeratorShops";
import ModeratorShopsView from "./components/ModeratorShopsView/ModeratorShopsView";

function App() {
    const [jwt, setJwt] = useLocalState("", "jwt");


    const [roles, setRoles] = useState(getRolesFromJWT());

    function getRolesFromJWT() {
        if (jwt) {
            const decodeJwt = jwt_decode(jwt);
            return decodeJwt.authorities;
        }
        return [];

    }

    return (
        <Routes>
            <Route path="/products"
                   element={
                       <PrivateRoute>
                           <Products/>
                       </PrivateRoute>
                   }/>
            <Route
                path="/shops"
                element={
                    roles.find((role) => role === 'moderator') ? (
                        <PrivateRoute>
                            <ModeratorShops/>
                        </PrivateRoute>
                    ) : (
                        <PrivateRoute>
                            <Shops/>
                        </PrivateRoute>
                    )
                }/>
            <Route
                path="/shops/:id"
                element={
                    roles.find((role) => role === 'moderator')
                        ?
                        <PrivateRoute>
                            <ModeratorShopsView/>
                        </PrivateRoute>
                        :
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
