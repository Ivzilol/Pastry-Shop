import './App.css';
import React from "react";
import 'bootstrap/dist/css/bootstrap.min.css';
import {Route, Routes} from "react-router-dom";
import Products from "./components/Products/Products";
import Homepage from "./components/homepage/Homepage";
import Login from "./components/Login/Login";

function App() {

    return (
        <Routes>
            <Route path="/products"
                   element={<Products/>}/>
        {/*//     <Route*/}
        {/*//         path="/shops"*/}
        {/*//         element={*/}
        {/*//             <PrivateRoute>*/}
        {/*//                 <Shops/>*/}
        {/*//             </PrivateRoute>*/}
        {/*//         }/>*/}
        {/*//     <Route*/}
        {/*//         path="/shops/:id"*/}
        {/*//         element={*/}
        {/*//             <PrivateRoute>*/}
        {/*//                 <ShopsView/>*/}
        {/*//             </PrivateRoute>*/}
        {/*//         }/>*/}
        //     <Route path="/homepage" element={<Homepage/>}></Route>
        //     <Route path="/login" element={<Login/>}></Route>
        </Routes>
    );
}

export default App;
