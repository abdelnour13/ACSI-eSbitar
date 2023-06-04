import usePopUp from "./usePopUp";

function useConfirmPopUp() {

    const [PopUp,ctrl] = usePopUp();

    const ConfirmPopUp = ({ content, onOkClicked,onCancelClicked }) => {
        return <PopUp>
            <div className="p-3" >
                {content}
                <div className="flex justify-between" >
                    <button className="p-2 rounded bg-gray-300" 
                        onClick={onCancelClicked}
                    >
                        cancel
                    </button>
                    <button className="p-2 rounded bg-green-500 text-white"
                        onClick={onOkClicked}
                    >
                        confirm
                    </button>
                </div>
            </div>
        </PopUp>
    }

    return [ConfirmPopUp,ctrl];

}

export default useConfirmPopUp;