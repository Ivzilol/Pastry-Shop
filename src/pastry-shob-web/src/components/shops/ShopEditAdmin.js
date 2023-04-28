import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";

const ShopEditAdmin = () => {

    const user = useUser();
    const shopId = window.location.href.split("/shops/")[1];
    let navigate = useNavigate();
    const [shop, setShop] = useState({
        town: "",
        address: "",
        number: null,
        status: ""
    });

    const [shopEnum, setShopEnum] = useState([]);
    const [shopStatus, setShopStatus] = useState([]);
    const prevShopVale = useRef(shop);

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = "";
                if (shopData.address === null) shopData.address = "";
                setShop(shopData);
                setShopEnum(shopResponse.shopsEnums);
                setShopStatus(shopResponse.statusEnums);
            });
    }, []);

    function updateShop(prop, value) {
        const newShop = {...shop};
        newShop[prop] = value;
        setShop(newShop);
    }

    function editShop() {
        ajax(`/api/shops/${shopId}`, "PUT", user.jwt, shop)
            .then(shopData => {
                setShop(shopData);
            })
            navigate("/shops");
    }

    return (
        <main className="shop-edit-admin">
            <NavBarAdmin/>
            {shop ? (
                <section className="shop-edit-admin-container">
                    {shop ? (
                        <div className="shop-edit-admin-container-items">
                            <article className="shop-edit-admin-container-item">
                                <h6>Name:</h6>
                                <input
                                    onChange={(e) => updateShop("name", e.target.value)}
                                    value={shop.name}
                                    type="text"
                                    name="name"
                                />
                            </article>
                            <article className="shop-edit-admin-container-item">
                                <h6>Town:</h6>
                                <input
                                    onChange={(e) => updateShop("town", e.target.value)}
                                    value={shop.town}
                                    type="text"
                                    name="town"
                                />
                            </article>
                            <article className="shop-edit-admin-container-item">
                                <h6>Address:</h6>
                                <input
                                    onChange={(e) => updateShop("address", e.target.value)}
                                    value={shop.address}
                                    type="text"
                                    name="address"
                                />
                            </article>
                            <section className="shop-edit-admin-container-item-buttons">
                                <button
                                    type="submit"
                                    onClick={() => editShop()}
                                >Change Shop</button>
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

export default ShopEditAdmin;