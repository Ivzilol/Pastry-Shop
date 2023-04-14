import {useUser} from "../../UserProvider/UserProvider";
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";
import {useNavigate} from "react-router-dom";
import NavBar from "../NavBar/NavBar";

const UserOrders = () => {
    const user = useUser();
    let navigate = useNavigate();
    const [products, setProducts] = useState(null);
    let allPrice = 0;

    useEffect(() => {
        ajax('api/orders', "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });
        if (!user.jwt) navigate('/login')
    }, [user.jwt]);

    function removeProductFromOrder(id) {
        ajax(`/api/orders/${id}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage()
            })
    }

    function refreshPage() {
        window.location.reload();
    }

    function confirmOrder() {
        ajax(`/api/orders`, "PATCH", user.jwt, {
            status : "confirmed"
        })
            .then(() =>
            refreshPage())
    }

    return (

        <main className="orders-user">
            <NavBar/>
            {products ? (
                <article className="orders-container">
                    {products.map((product) => (
                        <div className="orders-container-items"
                        key={product.id}
                        >
                            <p className="orders-container-items-name">
                               Product Name: {product.productName}
                            </p>
                            <p className="orders-container-items-name">
                                Product Price: {product.price}
                            </p>
                            <button
                            onClick={() => removeProductFromOrder(product.id)
                            }

                            >Remove Product</button>
                            <p className="getAllPrice">{allPrice += product.price}</p>
                        </div>
                    ))}
                    <h5>Тhe total amount of the order: {allPrice.toFixed(2)}</h5>
                    <button
                    onClick={() => confirmOrder()}
                    >Confirm order</button>
                </article>
            ) : (
                <></>
            )}
        </main>
    )
}

export default UserOrders;