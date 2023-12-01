import React, {Suspense} from 'react';
import ReactDOM from 'react-dom';
import './index.css';
import './i18n'
import App from './App';
import reportWebVitals from './reportWebVitals';
import {BrowserRouter} from "react-router-dom";
import {UserProvider} from "./UserProvider/UserProvider";
import Loading from "./components/Loading/Loading";


ReactDOM.render(
    <BrowserRouter>
        <UserProvider>
            <Suspense fallback={<Loading/>}>
                <App/>
            </Suspense>
        </UserProvider>
    </BrowserRouter>,
    document.getElementById('root')
);


reportWebVitals();
