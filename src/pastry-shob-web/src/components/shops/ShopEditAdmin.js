import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import baseURL from "../BaseURL/BaseURL";

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

    useRef(shop);
    useEffect(() => {
        ajax(`${baseURL}api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse;
                if (shopData.town === null) shopData.town = "";
                if (shopData.address === null) shopData.address = "";
                setShop(shopData);
            });
    }, [shopId, user.jwt]);

    function updateShop(prop, value) {
        const newShop = {...shop};
        newShop[prop] = value;
        setShop(newShop);
    }

    function editShop() {
        ajax(`${baseURL}api/shops/${shopId}`, "PATCH", user.jwt, shop)
            .then(shopData => {
                setShop(shopData);
            })
        navigate("/shops");
    }

    const emptyComment = {
        id: null,
        text: "",
        shopId: shopId !== null ? parseInt(shopId) : null,
        user: user.jwt
    }

    const [comment, setComment] = useState(emptyComment);
    const [allComments, setAllComments] = useState([]);

    useEffect(() => {
        ajax(`${baseURL}api/comments?shopId=${shopId}`, "GET", user.jwt)
            .then(commentsData => {
                setAllComments(commentsData);
            })
    }, [])


    function handleEditComment(commentId) {
        const index = allComments.findIndex(comment => comment.id === commentId);
        const commentCopy = {
            id: allComments[index].id,
            text: allComments[index].text,
            shopId: shopId !== null ? parseInt(shopId) : null,
            user: user.jwt
        }
        setComment(commentCopy);
    }

    function refreshPage() {
        window.location.reload();
    }

    function DeleteComment(id) {
        ajax(`${baseURL}api/comments/${id}`, "DELETE", user.jwt)
            .then((response) => {
                if (response.custom === 'Successful delete message') {
                    refreshPage();
                }
            });
    }

    function submitComment() {
        if (comment.id) {
            ajax(`${baseURL}api/comment/${comment.id}`, "PUT", user.jwt, comment)
                .then(d => {
                    const commentsCopy = [...allComments];
                    const index = commentsCopy.findIndex(comment => comment.id === d.id);
                    commentsCopy[index] = d;
                    setAllComments(commentsCopy);
                    setComment(emptyComment);
                })
        } else {
            ajax(`${baseURL}api/comments`, "POST", user.jwt, comment)
                .then(d => {
                    const commentCopy = [...allComments];
                    commentCopy.push(d);
                    setAllComments(commentCopy);
                    setComment(emptyComment);
                });
        }
    }

    function updateComment(value) {
        const commentCopy = {...comment}
        commentCopy.text = value
        setComment(commentCopy);
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
                                >Change Shop
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
            <section className="comments-admin">
                {/*<input className="comments-admin-input"*/}
                {/*       onChange={(e) => updateComment(e.target.value)}*/}
                {/*       value={comment.text}*/}
                {/*>*/}
                {/*</input>*/}
                <div className="admin-comments-view">
                    {allComments.map(currentComment => (
                        <section className="admin-comments-container"
                                 key={currentComment.id}
                        >
                            <article className="admin-comments-items">
                                <h6>{currentComment.createdBy.username}</h6>
                                <p>{currentComment.text}</p>
                                <button
                                    onClick={() => DeleteComment(currentComment.id)}
                                >Delete Comment
                                </button>
                            </article>

                        </section>
                    ))}
                </div>
            </section>
        </main>
    )
}

export default ShopEditAdmin;