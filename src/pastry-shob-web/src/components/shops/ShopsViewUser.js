import {useUser} from "../../UserProvider/UserProvider";
import React, {useEffect, useState} from "react";
import {useNavigate, useParams} from "react-router-dom";
import ajax from "../../Services/FetchService";
import NavBar from "../NavBar/NavBar";
import ShopArt from "../ShopArt/ShopArt";
import Footer from "../Footer/Footer";

const ShopsViewUser = () => {

    const user = useUser();
    const [shops, setShops] = useState(null);
    const {shopId} = useParams();
    let navigate = useNavigate();

    useEffect(() => {
        ajax("api/shops", "GET", user.jwt)
            .then(shopData => {
                setShops(shopData);
            });
        if (!user.jwt) navigate("/login");
    }, [user.jwt]);


    return (

        <main className="shops-view">
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