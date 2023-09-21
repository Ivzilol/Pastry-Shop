import {useUser} from "../../UserProvider/UserProvider";
import {useDispatch, useSelector} from 'react-redux';
import {useEffect, useState} from "react";
import ajax from "../../Services/FetchService";

const ChatRoom = () => {

    const user = useUser();
    const baseUrl = "http://localhost:8080/";



    return (
        <div>
            Proba
        </div>
    )
}

export default ChatRoom;