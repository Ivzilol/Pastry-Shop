import {Link} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import jwt_decode from "jwt-decode"

function NavBar() {

    // const navigate = useNavigate();
    // const {pathname} = useNavigate()
    const user = useUser();
    const [authorities, setAuthorities] = useState(null);

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
            <div>
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                                    user.setJwt(null);
                            window.location.href = "/";
                        }}
                    >
            Logout
          </button>
                ) : window.location.href !== "/login" ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            window.location.href = "/login";
                        }}
                    >
                        Login
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
                        Shops
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
                        Products
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
                        Order
                    </button>
                ) : (
                    <></>
                )}
                {user && user.jwt ? (
                    <button
                        className="nav-button"
                        onClick={() => {
                            window.location.href = "/users"
                        }}
                    >
                        Profile
                    </button>
                ) : (
                    <></>
                )}
            </div>
        </section>
    )
}

export default NavBar;