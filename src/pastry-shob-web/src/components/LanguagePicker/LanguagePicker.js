import {Form} from "react-bootstrap";
import {useTranslation} from "react-i18next";

const LanguagePicker = () => {


    const { i18n } = useTranslation();

    const handleLangChange = (evt) => {
        const lang = evt.target.value;
        localStorage.setItem("lang", lang);
        i18n.changeLanguage(lang);
    };

    return (
        <Form.Select
            style={{ width: "4rem", position: 'fixed', right: '0', marginRight: '10px', backgroundColor: '#ef7d00' }}
            size='sm'
            aria-label='Default select example'
            onChange={handleLangChange}
            value={localStorage.getItem("lang")}>
            <option label='Select language'>Select language</option>
            <option value='bg'>BG</option>
            <option value='en'>EN</option>
        </Form.Select>
    );
}

export default LanguagePicker;