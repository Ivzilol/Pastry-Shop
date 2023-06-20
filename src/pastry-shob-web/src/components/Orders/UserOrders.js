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
    const baseUrl = "http://localhost:8080/";

    useEffect(() => {
        ajax(`${baseUrl}api/orders`, "GET", user.jwt)
            .then(productsData => {
                setProducts(productsData);
            });
        if (!user.jwt) navigate('/login')
    }, [navigate, user.jwt]);

    function removeProductFromOrder(id) {
        ajax(`${baseUrl}api/orders/${id}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage()
            })
    }

    function refreshPage() {
        window.location.reload();
    }

    function confirmOrder() {
        ajax(`${baseUrl}api/orders`, "PATCH", user.jwt, {
            status: "confirmed"
        })
            .then(() =>
                refreshPage())
        alert("Successful confirm your order");
    }


    return (
        <main className="orders-user">
            <NavBar/>
            <h2 className="orders-user-title">Вашата поръчка</h2>
            <hr className="orders-user-line"/>
            {products ? (
                <article className="orders-container">
                    {products.map((product) => (

                        <div className="orders-container-items"
                             key={product.id}
                        >
                            <p className="orders-container-items-name">
                                Име: {product.productName}
                            </p>
                            <p className="orders-container-items-name">
                                Цена: {product.price} лв.
                            </p>
                            {product.status === 'newOrder'
                                ?
                                <button className="orders-container-items-button"
                                        onClick={() => removeProductFromOrder(product.id)
                                        }
                                >Премахнете продукта
                                </button>
                                :
                                <></>
                            }
                            <p className="getAllPrice">{allPrice += product.price}</p>
                        </div>
                    ))}
                </article>
            ) : (
                <></>
            )}
            <section>
                {allPrice > 0 ? (
                    <div className="orders-user-price">
                        <h5 className="orders-user-title"
                        >Обща цена на поръчката: {allPrice.toFixed(2)}</h5>
                        <button
                            className="orders-container-items-button-confirm"
                            onClick={() => confirmOrder()}
                        >Потвърди поръчката
                        </button>
                    </div>
                ) : (
                    <div className="order-user-tracker">
                        <h5 className="order-user-tracker-title">
                            Ако имате недоставени от нас поръчки можете да ги проследите от тук:
                        </h5>
                        <button
                            className="order-user-tracker-button"
                            onClick={() => window.location.href = "/orders/tracking"}
                        >Проследи
                        </button>
                        <button
                            className="order-user-tracker-button"
                            onClick={() => window.location.href = "/orders/history/user"}
                        >История на Вашите поръчки
                        </button>
                    </div>
                )}
            </section>
        </main>
    )
}

export default UserOrders;