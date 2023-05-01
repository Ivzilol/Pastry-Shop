import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";

const UserEditProfile = () => {
    const user = useUser();
    const userId = window.location.href.split("/users/")[1];
    let navigate = useNavigate();
    const [currentUser, setCurrentUser] = useState({
        username: "",
        firstName: "",
        lastName: "",
        email: "",
        address: ""
    });

    useEffect(() => {
        ajax(`/api/users/${userId}`, "GET", user.jwt)
            .then(userResponse => {
                setCurrentUser(userResponse);
            });
        if (!user.jwt) navigate("/login");
    }, [user.jwt])

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
                                    value={currentUser.username}
                                    type="text"
                                    name="username"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>First Name:</h6>
                                <input
                                    value={currentUser.firstName}
                                    type="text"
                                    name="firstName"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>Last Name:</h6>
                                <input
                                    value={currentUser.lastName}
                                    type="text"
                                    name="lastName"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>Email:</h6>
                                <input
                                    value={currentUser.email}
                                    type="text"
                                    name="email"
                                />
                            </article>
                            <article className="user-edit-profile-item">
                                <h6>Address:</h6>
                                <input
                                    value={currentUser.address}
                                    type="text"
                                    name="address"
                                />
                            </article>
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