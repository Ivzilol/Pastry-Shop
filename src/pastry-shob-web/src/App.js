import './App.css';
import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Routes} from "react-router-dom";
import ProductsAdmin from "./components/ProductsAdmin/ProductsAdmin";
import Homepage from "./components/homepage/Homepage";
import Login from "./components/Login/Login";
import PrivateRoute from "./PriviteRoute/PrivateRoute";
import Shops from "./components/shops/Shops";
import ShopsView from "./ShopsView/ShopsView";
import jwt_decode from 'jwt-decode'
import ModeratorShops from "./components/ModeratorShops/ModeratorShops";
import ModeratorShopsView from "./components/ModeratorShopsView/ModeratorShopsView";
import {useUser} from "./UserProvider/UserProvider";
import Register from "./components/Register/Register";
import CreateProductAdmin from "./components/ProductsAdmin/CreateProductAdmin";
import EditProductsAdmin from "./components/ProductsAdmin/EditProductsAdmin";
import ProductsUser from "./components/ProductsUser/ProductsUser";

function App() {
    // const [jwt, setJwt] = useLocalState("", "jwt");
    const user = useUser();
    const [roles, setRoles] = useState(getRolesFromJWT());

    useEffect(() => {
        setRoles(getRolesFromJWT())
    }, [user.jwt])

    function getRolesFromJWT() {
        if (user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            return decodeJwt.authorities;
        }
        return [];

    }

    return (
        <Routes>
            <Route path="/products"
                   element={
                        roles.find((role) => role === 'admin') ? (
                            <PrivateRoute>
                                <ProductsAdmin/>
                            </PrivateRoute>
                        ) : (
                            <PrivateRoute>
                                <ProductsUser/>
                            </PrivateRoute>
                        )
                   }/>

            <Route path="/products/create"
                   element={
                       roles.find((role) => role === 'admin') ? (
                           <PrivateRoute>
                               <CreateProductAdmin/>
                           </PrivateRoute>
                       ) : (
                           <PrivateRoute>
                               <ProductsUser/>
                           </PrivateRoute>
                       )
                   }/>
            <Route path="/products/:productId"
                   element={
                       roles.find((role) => role === 'admin') ? (
                           <PrivateRoute>
                               <EditProductsAdmin/>
                           </PrivateRoute>
                       ) : (
                           <PrivateRoute>
                               <ProductsUser/>
                           </PrivateRoute>
                       )
                   }
            />
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
                path="/shops/:shopId"
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
            <Route path="/register" element={<Register/>}></Route>
        </Routes>
    );
}

export default App;
