import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import OrderWindow from "../Orders/OrderWindow";
import {useTranslation} from "react-i18next";

const UserEditProfile = () => {
    const user = useUser();
    const userId = window.location.href.split("/users/")[1];
    let navigate = useNavigate();
    const {t} = useTranslation();
    const baseUrl = "http://localhost:8080/";
    const [currentUser, setCurrentUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        address: "",
        phoneNumber: ""
    });

    useEffect(() => {
        ajax(`${baseUrl}api/users/${userId}`, "GET", user.jwt)
            .then(userResponse => {
                setCurrentUser(userResponse);
            });
        if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt, userId])

    function updateUser(prop, value) {
        const newUser = {...currentUser}
        newUser[prop] = value;
        setCurrentUser(newUser);
    }

    function editProfile() {
        if (
            currentUser.username.trim() === '' ||
            currentUser.firstName.trim() === '' ||
            currentUser.lastName.trim() === '' ||
            currentUser.email.trim() === '' ||
            currentUser.address.trim() === '' ||
            currentUser.phoneNumber.trim() === ''
        ) {
            alert("Format cannot contain empty fields");
            return;
        }
        ajax(`${baseUrl}api/users/edit/${userId}`, "PATCH", user.jwt, currentUser)
            .then(userData => {
                if (userData.custom === 'Successful update user!') {
                    alert("Successful update your personal info, please login again in your profile")
                    user.setJwt(null);
                } else {
                    alert(userData.custom);
                    navigate("/users");
                }
            });
    }

    return (
        <main className="user-edit-profile">
            <NavBar/>
            <OrderWindow/>
            {currentUser ? (
                <section className="user-edit-profile-container">
                    {currentUser ? (
                        <div className="user-edit-profile-items">
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.username')}</h6>
                                <input
                                    onChange={(e) => updateUser("username", e.target.value)}
                                    value={currentUser.username}
                                    type="text"
                                    name="username"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.first-name')}</h6>
                                <input
                                    onChange={(e) => updateUser("firstName", e.target.value)}
                                    value={currentUser.firstName}
                                    type="text"
                                    name="firstName"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.last-name')}</h6>
                                <input
                                    onChange={(e) => updateUser("lastName", e.target.value)}
                                    value={currentUser.lastName}
                                    type="text"
                                    name="lastName"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.email')}</h6>
                                <input
                                    onChange={(e) => updateUser("email", e.target.value)}
                                    value={currentUser.email}
                                    type="text"
                                    name="email"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.address')}</h6>
                                <input
                                    onChange={(e) => updateUser("address", e.target.value)}
                                    value={currentUser.address}
                                    type="text"
                                    name="address"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.phone-number')}</h6>
                                <input
                                    onChange={(e) => updateUser("phoneNumber", e.target.value)}
                                    value={currentUser.phoneNumber}
                                    type="text"
                                    name="phoneNumber"
                                />
                            </article>
                            <section className="user-edit-profile-button">
                                <button
                                    type="submit"
                                    onClick={() => editProfile()}
                                >
                                    {t('user-profile.edit-button')}
                                </button>
                            </section>
                        </div>
                    ) : (
                        <></>
                    )}
                </section>
            ) : (
                <></>
            )}
        </main>
    )
}

export default UserEditProfile;