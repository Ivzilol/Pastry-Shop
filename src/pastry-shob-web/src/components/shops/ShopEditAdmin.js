import {useUser} from "../../UserProvider/UserProvider";
import {useNavigate} from "react-router-dom";
import {useEffect, useRef, useState} from "react";
import ajax from "../../Services/FetchService";
import NavBarAdmin from "../NavBarAdmin/NavBarAdmin";
import Comment from "../Comment/Comment";

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

    const emptyComment = {
        id: null,
        text: "",
        shopId: shopId !== null ? parseInt(shopId) : null,
        user: user.jwt
    }

    const [comment, setComment] = useState(emptyComment);
    const [allComments, setAllComments] = useState([]);

    useEffect(() => {
        ajax(`/api/comments?shopId=${shopId}`, "GET", user.jwt)
            .then(commentsData => {
                setAllComments(commentsData);
            })
    })


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

    function handleDeleteComment() {
        console.log('Delete ', comment)
    }

    function submitComment() {
        if (comment.id) {
            ajax(`/api/comment/${comment.id}`, "PUT", user.jwt, comment)
                .then(d => {
                    const commentsCopy = [...allComments];
                    const index = commentsCopy.findIndex(comment => comment.id === d.id);
                    commentsCopy[index] = d;
                    setAllComments(commentsCopy);
                    setComment(emptyComment);
                })
        } else {
            ajax(`/api/comments`, "POST", user.jwt, comment)
                .then(d => {
                    const commentCopy = [...allComments];
                    commentCopy.push(d);
                    setAllComments(commentCopy);
                    setComment(emptyComment);
                })
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
                <textarea
                    onChange={(e) => updateComment(e.target.value)}
                    value={comment.text}
                >
                </textarea>
                <div className="admin-comments-view">
                    {allComments.map(currentComment => (
                        <Comment
                            createdBy={currentComment.createdBy}
                            text={currentComment.text}
                            id={currentComment.id}
                        />
                    ))}
                </div>
            </section>
        </main>
    )
}

export default ShopEditAdmin;