import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import OrderWindow from "../Orders/OrderWindow";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";

const UserProfile = () => {
    const user = useUser();
    const [currentUser, setCurrentUser] = useState(null);
    let navigate = useNavigate();
    const {t} = useTranslation();

    useEffect(() => {
        ajax(`${baseURL}api/users`, "GET", user.jwt)
            .then(userData => {
                setCurrentUser(userData);
            })
            if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt]);


    return (
        <main className="user-profile">
            <NavBar/>
            <OrderWindow/>
            <h3 className="user-profile-title">Your personal info</h3>
            {currentUser ? (
                <section
                    id={currentUser.id}
                    className="user-profile-container-items">
                    <h6>{t('user-profile.username')} {currentUser.username}</h6>
                    <p>{t('user-profile.first-name')} {currentUser.firstName}</p>
                    <p>{t('user-profile.last-name')} {currentUser.lastName}</p>
                    <p>{t('user-profile.email')} {currentUser.email}</p>
                    <p>{t('user-profile.address')} {currentUser.address}</p>
                    <p>{t('user-profile.phone-number')} {currentUser.phoneNumber}</p>
                    <button
                    onClick={() => {
                        window.location.href = `/users/${currentUser.id}`;
                    }}
                    >{t('user-profile.edit-button')}</button>
                    <button
                        onClick={() => {
                            window.location.href = `users/change-password`;
                        }}
                    >{t('user-change-pass.button')}</button>
                </section>
            ) : (
                <></>
            )}
        </main>
    )
}

export default UserProfile