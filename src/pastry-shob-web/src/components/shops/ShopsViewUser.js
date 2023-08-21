import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import ShopArt from "../ShopArt/ShopArt";
import Footer from "../Footer/Footer";
import OrderWindow from "../Orders/OrderWindow";

const ShopsViewUser = () => {

    const user = useUser();
    const [shops, setShops] = useState(null);
    let navigate = useNavigate();
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/shops`, "GET", user.jwt)
            .then(shopData => {
                setShops(shopData);
            });
        if (!user.jwt) navigate("/login");
    }, [navigate, user.jwt]);


    return (

        <main className="shops-view">
            <OrderWindow/>
            <NavBar/>
            <ShopArt/>
            {shops ? (
                <section className="shops-view-container">
                    {shops.map((shops) => (
                        <article
                            key={shops.id}
                            >
                            <h3 className="shops-view-container-comment-title">
                                Вашето менение е важно за нас</h3>
                            <button className="shops-view-button"
                                id="submit"
                                type="button"
                                onClick={() => {
                                    window.location.href = `/shops/${shops.id}`
                                }}
                            >
                                Напишете Вашия коментар
                            </button>
                        </article>
                    ))}
                </section>
            ) : (
                <></>
            )}
            <Footer/>
        </main>
    );
}

export default ShopsViewUser;