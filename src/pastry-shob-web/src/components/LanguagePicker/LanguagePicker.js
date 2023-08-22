import {DropdownButton} from "react-bootstrap";
import DropdownItem from "react-bootstrap/DropdownItem";
import {useTranslation} from "react-i18next";

const LanguagePicker = () => {
    const { i18 } = useTranslation();
    let languageHandlerChange = (lang) => {
        i18.changeLanguage(lang);
    }



    return(
        <div className="language-picker">
            <DropdownButton title="EN" className="language-picker--dropdown">
                <DropdownItem onClick={() => languageHandlerChange('en')}>English</DropdownItem>
                <DropdownItem onClick={() => languageHandlerChange('bg')}>Български</DropdownItem>
            </DropdownButton>
        </div>
    )
}

export default LanguagePicker;