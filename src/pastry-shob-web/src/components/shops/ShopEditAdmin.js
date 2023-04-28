import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

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

    useEffect(() => {
        ajax(`/api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = "";
                if (shopData.address === null) shopData.address = "";
                setShop(shopData);
                setShopEnum(shopResponse.shopsEnums);
                setShopStatus(shopResponse.statusEnums);
            })
    })

    return (
        <main className="shop-edit-admin">
            {shop ? (
                <section>

                </section>
            ) : (
                <></>
            )}

        </main>
    )
}

export default ShopEditAdmin;