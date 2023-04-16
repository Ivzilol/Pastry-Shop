import './App.css';
import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Routes} from "react-router-dom";
import ProductsAdmin from "./components/Products/ProductsAdmin";
import Homepage from "./components/homepage/Homepage";
import Login from "./components/Login/Login";
import PrivateRoute from "./PriviteRoute/PrivateRoute";
import ShopsViewAdmin from "./components/shops/ShopsViewAdmin";
import ShopsView from "./ShopsView/ShopsView";
import jwt_decode from 'jwt-decode'
import AdminShops from "./components/ModeratorShops/ModeratorShops";
import ModeratorShopsView from "./components/ModeratorShopsView/ModeratorShopsView";
import {useUser} from "./UserProvider/UserProvider";
import Register from "./components/Register/Register";
import CreateProductAdmin from "./components/Products/CreateProductAdmin";
import EditProductsAdmin from "./components/Products/EditProductsAdmin";
import ProductsUser from "./components/Products/ProductsUser";
import ShopsViewUser from "./components/shops/ShopsViewUser";
import UserOrders from "./components/Orders/UserOrders";
import AdminOrders from "./components/Orders/AdminOrders";

function App() {
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
                    roles.find((role) => role === 'admin') ? (
                        <PrivateRoute>
                            <ShopsViewAdmin/>
                        </PrivateRoute>

                    ) : (
                        <PrivateRoute>
                            <ShopsViewUser/>
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
            <Route path="/orders"
                   element={
                       roles.find((role) => role === 'admin')
                           ?
                           <PrivateRoute>
                               <AdminOrders/>
                           </PrivateRoute>
                           :
                           <PrivateRoute>
                               <UserOrders/>
                           </PrivateRoute>
                   }/>
            <Route path="/" element={<Homepage/>}></Route>
            <Route path="/login" element={<Login/>}></Route>
            <Route path="/register" element={<Register/>}></Route>
        </Routes>
    );
}

export default App;
