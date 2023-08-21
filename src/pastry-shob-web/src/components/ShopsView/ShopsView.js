import React, {useEffect, useRef, useState} from 'react';
import ajax from "../../Services/FetchService";
import {useNavigate, useParams} from "react-router-dom";
import {useUser} from "../../UserProvider/UserProvider";
import Comment from "../Comment/Comment";
import NavBar from "../NavBar/NavBar";
import ShopArt from "../ShopArt/ShopArt";
import Footer from "../Footer/Footer";
import OrderWindow from "../Orders/OrderWindow";

const ShopsView = () => {
    useNavigate();
    const user = useUser();
    const {shopId} = useParams()
    // const shopId = window.location.href.split("/shops/")[1];
    const baseUrl = "http://localhost:8080/";
    const [shop, setShop] = useState({
        town: "",
        address: "",
        number: null,
        status: ""
    });

    const [, setShopsEnums] = useState([]);
    const [shopsStatuses, setShopsStatuses] = useState([]);
    const prevShopValue = useRef(shop);

    const emptyComment = {
        id: null,
        text: "",
        shopId: shopId !== null ? parseInt(shopId) : null,
        user: user.jwt,
    }

    const [comment, setComment] = useState(emptyComment);
    const [comments, setComments] = useState([]);


    function handleEditComment(commentId) {
        const index = comments.findIndex(comment => comment.id === commentId);
        const commentCopy = {
            id: comments[index].id,
            text: comments[index].text,
            shopId: shopId !== null ? parseInt(shopId) : null,
            user: user.jwt,
        }
        setComment(commentCopy);
    }

    function refreshPage() {
        window.location.reload();
    }

    function handleDeleteComment(commentId) {
        ajax(`${baseUrl}api/comments/user/${commentId}`, "DELETE", user.jwt)
            .then(() => {
                refreshPage();
            });
    }

    function submitComment() {
        if (comment.id) {
            ajax(`${baseUrl}api/comments/${comment.id}`, "PUT", user.jwt, comment).then(d => {
                const commentsCopy = [...comments]
                const index = commentsCopy.findIndex(comment => comment.id === d.id);
                commentsCopy[index] = d;
                setComments(commentsCopy);
                setComment(emptyComment);
            })
        } else {
            ajax(`${baseUrl}api/comments`, 'POST', user.jwt, comment).then(d => {
                const commentsCopy = [...comments]
                commentsCopy.push(d);
                setComments(commentsCopy);
                setComment(emptyComment);
            });
        }
    }

    useEffect(() => {
        ajax(`${baseUrl}api/comments?shopId=${shopId}`,
            'GET',
            user.jwt,
            null)
            .then(commentsData => {
                setComments(commentsData)
            });
    }, [shopId, user.jwt])

    function updateComment(value) {
        const commentCopy = {...comment}
        commentCopy.text = value;
        setComment(commentCopy);
    }

    function updateShop(prop, value) {
        const newShop = {...shop}
        newShop[prop] = value;
        setShop(newShop);
    }

    function saveShop() {
        if (shop.status === shopsStatuses[0].status) {
            updateShop("status", shopsStatuses[1].status)
        } else {
            persist();
        }
    }

    // eslint-disable-next-line react-hooks/exhaustive-deps
    function persist() {
        ajax(`${baseUrl}api/shops/${shopId}`, "PUT", user.jwt, shop)
            .then(shopData => {
                    setShop(shopData);
                }
            );
    }

    useEffect(() => {
        if (prevShopValue.current.status !== shop.status) {
            persist();
        }
        prevShopValue.current = shop;
    }, [persist, shop])

    useEffect(() => {
        ajax(`${baseUrl}api/shops/${shopId}`, "GET", user.jwt)
            .then(shopResponse => {
                let shopData = shopResponse.shops;
                if (shopData.town === null) shopData.town = ""
                if (shopData.address === null) shopData.address = ""
                setShop(shopData);
                setShopsEnums(shopResponse.shopsEnums);
                setShopsStatuses(shopResponse.statusEnums);
            });
    }, [shopId, user.jwt]);

    return (
        <main className="user-comments-container">
            <NavBar/>
            <ShopArt/>
            <OrderWindow/>
            <div className="comments">
                        <textarea placeholder="Напиешете Вашето мнение тук..."
                            className="comments-textarea"
                            onChange={(e) => updateComment(e.target.value)}
                            value={comment.text}
                        >
                        </textarea>
                <button className="user-comments-container-button"
                    onClick={() => submitComment()}
                >Post Comment
                </button>
            </div>
            <div className="comments-view">
                {comments.map(currentComment => (
                    <Comment
                        createdBy={currentComment.createdBy}
                        text={currentComment.text}
                        emitDeleteComment={handleDeleteComment}
                        emitEditComment={handleEditComment}
                        id={currentComment.id}
                    />
                ))}
            </div>
            <Footer/>
        </main>
    );
};

export default ShopsView;