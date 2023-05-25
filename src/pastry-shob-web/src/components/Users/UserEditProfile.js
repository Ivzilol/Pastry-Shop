import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";

const UserEditProfile = () => {
    const user = useUser();
    const userId = window.location.href.split("/users/")[1];
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";
    const [currentUser, setCurrentUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        address: ""
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
        ajax(`${baseUrl}api/users/edit/${userId}`, "PATCH", user.jwt, currentUser)
            .then(userData => {
                setCurrentUser(userData);
            });
        navigate("/users");
    }

    return (
        <main className="user-edit-profile">
            <NavBar/>
            {currentUser ? (
                <section className="user-edit-profile-container">
                    {currentUser ? (
                        <div className="user-edit-profile-items">
                            <article className="user-edit-profile-item">
                                <h6>Username:</h6>
                                <input
                                    onChange={(e) => updateUser("username", e.target.value)}
                                    value={currentUser.username}
                                    type="text"
                                    name="username"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>First Name:</h6>
                                <input
                                    onChange={(e) => updateUser("firstName", e.target.value)}
                                    value={currentUser.firstName}
                                    type="text"
                                    name="firstName"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>Last Name:</h6>
                                <input
                                    onChange={(e) => updateUser("lastName", e.target.value)}
                                    value={currentUser.lastName}
                                    type="text"
                                    name="lastName"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>Email:</h6>
                                <input
                                    onChange={(e) => updateUser("email", e.target.value)}
                                    value={currentUser.email}
                                    type="text"
                                    name="email"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>Address:</h6>
                                <input
                                    onChange={(e) => updateUser("address", e.target.value)}
                                    value={currentUser.address}
                                    type="text"
                                    name="address"
                                />
                            </article>
                            <section className="user-edit-profile-button">
                                <button
                                type="submit"
                                onClick={() => editProfile()}
                                >
                                    Edit Profile
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