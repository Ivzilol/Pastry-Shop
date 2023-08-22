import {Link} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import jwt_decode from "jwt-decode"
import LanguagePicker from "../LanguagePicker/LanguagePicker";

function NavBar() {

    const user = useUser();
    const [authorities, setAuthorities] = useState(null);
    const [roles, setRoles] = useState(getRolesFromJWT());

    useEffect(() => {
        setRoles(getRolesFromJWT())
    }, [])

    // eslint-disable-next-line react-hooks/exhaustive-deps
    function getRolesFromJWT() {
        if (user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            return decodeJwt.sub;
        }
        return [];
    }


    useEffect(() => {
        if (user && user.jwt) {
            const decodeJwt = jwt_decode(user.jwt);
            setAuthorities(decodeJwt.authorities);
        }
    }, [user]);


    return (
        <section className="nav">
            <article className="nav-home">
                <a href="/">СЛАДКАРНИЦАТА НА МАМА</a>
            </article>
            <LanguagePicker/>
            <div>
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                                    user.setJwt(null);
                            window.location.href = "/";
                        }}
                    >
            Изход
          </button>
                ) : window.location.href !== "/login" ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            window.location.href = "/login";
                        }}
                    >
                        Влизане
                    </button>
                ) : (
                    <></>
                )}
                {/*Todo: put moderator*/}
                {authorities &&
                authorities.filter((auth) => auth === "ROLE_INSTRUCTOR").length > 0 ? (
                    <Link
                        className="ms-5 ms-md-5 me-md-5 link"
                        to="/instructors/dashboard"
                    >
                        Moderator
                    </Link>
                ) : (
                    <></>
                )}

                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            window.location.href = "/shops";
                        }}
                    >
                        Магазини
                    </button>
                ) : (
                    <></>
                )}

                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            window.location.href = "/products";
                        }}
                    >
                        Продукти
                    </button>
                ) : (
                    <></>
                )}
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            window.location.href = "/orders";
                        }}
                    >
                        Поръчки
                    </button>
                ) : (
                    <></>
                )}
                {user && user.jwt ? (
                    <button
                        className="nav-button-user"
                        onClick={() => {
                            window.location.href = "/users"
                        }}
                    >
                        {roles}
                    </button>

                ) : (
                    <></>
                )}
            </div>
        </section>
    )
}

export default NavBar;