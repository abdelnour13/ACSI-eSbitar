import { useState } from "react";

function usePopUp() {

    const [isHidden, setIsHidden] = useState(true);

    const open = () => setIsHidden(false);

    const close = () => setIsHidden(true);

    const handleParentClick = (ev) => {
        if (ev.target === ev.currentTarget) {
            console.log('hiii')
            setIsHidden(true);
        }
    }

    const popUp = ({ children,rootClassName }) => {

        return (
            !isHidden ? <div className="fixed inset-0 bg-[#000000B3] flex 
                justify-center items-center"
                onClick={handleParentClick}
            >
                <div className={"bg-white "+rootClassName}>
                    <div>
                        {children}
                    </div>
                </div>
            </div> : null
        );

    }

    return [popUp, {
        isHidden, open, close
    }];

}

export default usePopUp;