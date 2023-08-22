import {DropdownButton} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";

const LanguagePicker = () => {
    return(
        <div className="language-picker">
            <DropdownButton title="EN" className="language-picker--dropdown">
                <DropdownItem>Български</DropdownItem>
                <DropdownItem>English</DropdownItem>
            </DropdownButton>
        </div>
    )
}

export default LanguagePicker;