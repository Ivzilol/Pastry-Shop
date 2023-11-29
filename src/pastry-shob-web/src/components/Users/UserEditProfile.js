import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import React, {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import {useTranslation} from "react-i18next";
import baseURL from "../BaseURL/BaseURL";
import {FontAwesomeIcon} from "@fortawesome/react-fontawesome";
import {faInfoCircle} from "@fortawesome/free-solid-svg-icons";

const UserEditProfile = () => {
    const user = useUser();
    const userId = window.location.href.split("/users/")[1];
    let navigate = useNavigate();
    const [errorUpdateProfile, setErrorUpdateProfile] = useState(false);
    let error = false;
    const {t} = useTranslation();
    const [currentUser, setCurrentUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        address: "",
        phoneNumber: ""
    });

    useEffect(() => {
        ajax(`${baseURL}api/users/${userId}`, "GET", user.jwt)
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

    const [updateError] = useState({
        usernameError: null,
        firstNameError: null,
        lastNameError: null,
        emailError: null,
        addressError: null,
        phoneError: null
    })

    function editProfile() {
        if (currentUser.username.trim().length < 3 || currentUser.username.trim().length > 20) {
            updateError.usernameError = "Username length must be between 3 and 20 characters";
            error = true;
        }
        if (currentUser.firstName.trim() === '') {
            updateError.firstNameError = "First Name cannot be empty";
            error = true;
        }
        if (currentUser.lastName.trim() === '') {
            updateError.lastNameError = "Last Name cannot be empty";
            error = true;
        }
        if (currentUser.email.trim() === '') {
            updateError.emailError = "Email cannot be empty";
            error = true;
        }
        if (currentUser.address.trim() === '') {
            updateError.addressError = "Address number cannot be empty";
            error = true;
        }
        if (currentUser.phoneNumber.trim() === '') {
            updateError.phoneError = "Phone number number cannot be empty";
            error = true;
        }
        if (error) {
            setErrorUpdateProfile(true);
            return;
        }
        ajax(`${baseURL}api/users/edit/${userId}`, "PATCH", user.jwt, currentUser)
            .then(userData => {
                if (userData.custom === 'Successful update user!') {
                    alert("Successful update your personal info, please login again in your profile")
                    user.setJwt(null);
                } else {
                    updateError.usernameError = 'Username or Email already exists!'
                    updateError.emailError = 'Username or Email already exists!'
                    setErrorUpdateProfile(true);
                }
            });
    }

    return (
        <main className="user-edit-profile">
            <NavBar/>
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
                            {errorUpdateProfile && updateError.usernameError !== null &&
                                <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {updateError.usernameError}</span>
                            }
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.first-name')}</h6>
                                <input
                                    onChange={(e) => updateUser("firstName", e.target.value)}
                                    value={currentUser.firstName}
                                    type="text"
                                    name="firstName"
                                />
                            </article>
                            {errorUpdateProfile && updateError.firstNameError !== null &&
                                <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {updateError.firstNameError}</span>
                            }
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.last-name')}</h6>
                                <input
                                    onChange={(e) => updateUser("lastName", e.target.value)}
                                    value={currentUser.lastName}
                                    type="text"
                                    name="lastName"
                                />
                            </article>
                            {errorUpdateProfile && updateError.lastNameError !== null &&
                                <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {updateError.lastNameError}</span>
                            }
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.email')}</h6>
                                <input
                                    onChange={(e) => updateUser("email", e.target.value)}
                                    value={currentUser.email}
                                    type="text"
                                    name="email"
                                />
                            </article>
                            {errorUpdateProfile && updateError.emailError !== null &&
                                <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {updateError.emailError}</span>
                            }
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.address')}</h6>
                                <input
                                    onChange={(e) => updateUser("address", e.target.value)}
                                    value={currentUser.address}
                                    type="text"
                                    name="address"
                                />
                            </article>
                            {errorUpdateProfile && updateError.addressError !== null &&
                                <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {updateError.addressError}</span>
                            }
                            <article className="user-edit-profile-item">
                                <h6>{t('user-profile.phone-number')}</h6>
                                <input
                                    onChange={(e) => updateUser("phoneNumber", e.target.value)}
                                    value={currentUser.phoneNumber}
                                    type="text"
                                    name="phoneNumber"
                                />
                            </article>
                            {errorUpdateProfile && updateError.phoneError !== null &&
                                <span id="validate-username"> <FontAwesomeIcon icon={faInfoCircle}/> {updateError.phoneError}</span>
                            }
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