import './App.css';
import React, {useEffect, useState} from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Routes} from "react-router-dom";
import ProductsAdmin from "./components/Products/ProductsAdmin";
import Homepage from "./components/homepage/Homepage";
import Login from "./components/Login/Login";
import PrivateRoute from "./PriviteRoute/PrivateRoute";
import ShopsViewAdmin from "./components/shops/ShopsViewAdmin";
import ShopsView from "./components/ShopsView/ShopsView";
import jwt_decode from 'jwt-decode'
import {useUser} from "./UserProvider/UserProvider";
import Register from "./components/Register/Register";
import CreateProductAdmin from "./components/Products/CreateProductAdmin";
import EditProductsAdmin from "./components/Products/EditProductsAdmin";
import ProductsUser from "./components/Products/ProductsUser";
import ShopsViewUser from "./components/shops/ShopsViewUser";
import UserOrders from "./components/Orders/UserOrders";
import AdminOrders from "./components/Orders/AdminOrders";
import ShopEditAdmin from "./components/shops/ShopEditAdmin";
import 'typeface-lobster';
import AdminUsers from "./components/Users/AdminUsers";
import UserProfile from "./components/Users/UserProfile";
import UserEditProfile from "./components/Users/UserEditProfile";
import UserOrderTracking from "./components/Orders/UserOrderTracking";
import Author from "./components/Author/Author";
import ProductPies from "./components/Products/ProductPies";
import ProductBuns from "./components/Products/ProductBuns";
import ProductSweets from "./components/Products/ProductSweets";
import ProductCake from "./components/Products/ProductCake";
import AdminOrdersHistory from "./components/Orders/AdminOrdersHistory";
import UserOrdersHistory from "./components/Orders/UserOrdersHistory";
import ConfirmRegister from "./components/Register/ConfirmRegister";
import UserChangePassword from "./components/Users/UserChangePassword";
import ForgottenPasswordEmail from "./components/Login/ForgottenPasswordEmail";
import ForgottenPasswordNewPassword from "./components/Login/ForgottenPasswordNewPassword";
import ChatRoomsAdmin from "./components/ChatRoom/ChatRoomsAdmin";
import ChatRoomCurrentUser from "./components/ChatRoom/ChatRoomCurrentUser";
import NotFoundPage from "./components/NotFoundPage/NotFoundPage";
import Monitoring from "./components/Monitoring/Monitoring";

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
                           <EditProductsAdmin/>
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
                        <ShopsViewUser/>
                    )
                }/>
            <Route
                path="/shops/:shopId"
                element={
                    roles.find((role) => role === 'admin')
                        ?
                        <PrivateRoute>
                            <ShopEditAdmin/>
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
                           <AdminOrders/>
                           :
                           <PrivateRoute>
                               <UserOrders/>
                           </PrivateRoute>
                   }/>
            <Route path="/orders/history/user"
                   element={
                       roles.find((role) => role === 'admin')
                           ?
                           <PrivateRoute>
                               <AdminOrdersHistory/>
                           </PrivateRoute>
                           :
                           <PrivateRoute>
                               <UserOrdersHistory/>
                           </PrivateRoute>

                   }/>
            <Route path="/users"
                   element={
                       roles.find((role) => role === 'admin')
                           ?
                           <PrivateRoute>
                               <AdminUsers/>
                           </PrivateRoute>
                           :
                           <PrivateRoute>
                               <UserProfile/>
                           </PrivateRoute>
                   }
            />
            <Route path="/chat-room"
                   element={
                       roles.find((role) => role === 'admin')
                           ?
                           <PrivateRoute>
                               <ChatRoomsAdmin/>
                           </PrivateRoute>
                           :
                           <></>
                   }
            />
            <Route path="/chat-room/:username" element={<ChatRoomCurrentUser/>}/>
            <Route path={"/orders/tracking"} element={<UserOrderTracking/>}/>
            <Route path="/users/:userId" element={<UserEditProfile/>}/>
            <Route path="users/change-password" element={<UserChangePassword/>}/>
            <Route path="/forgotten-password" element={<ForgottenPasswordEmail/>}/>
            <Route path="/forgotten-password/:verificationCode" element={<ForgottenPasswordNewPassword/>}/>
            <Route path="/" element={<Homepage/>}></Route>
            <Route path="/login" element={<Login/>}></Route>
            <Route path="/register" element={<Register/>}></Route>
            <Route path="/register/verify/:verificationCode" element={<ConfirmRegister/>}/>
            <Route path="/author" element={<Author/>}></Route>
            <Route path="/products/pies" element={<ProductPies/>}></Route>
            <Route path="/products/buns" element={<ProductBuns/>}></Route>
            <Route path="/products/sweets" element={<ProductSweets/>}></Route>
            <Route path="/products/cake" element={<ProductCake/>}></Route>
            <Route path="*" element={<NotFoundPage/>}></Route>
            <Route path="/monitoring" element={<Monitoring/>}></Route>
        </Routes>
    );
}

export default App;
