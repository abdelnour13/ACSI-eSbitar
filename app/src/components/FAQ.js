import up from "../icons/up-arrow.png";
import down from "../icons/down-arrow.png";
import { useState } from "react";

const faqs = [
    {
      question: "How does telemedicine work?",
      answer: "Telemedicine allows you to consult with healthcare professionals remotely using video calls or messaging. You can discuss your symptoms, receive medical advice, and even get prescriptions without visiting a physical clinic."
    },
    {
      question: "Is my personal information secure on your platform?",
      answer: "Yes, protecting your privacy and data security is our top priority. We have robust security measures in place to safeguard your personal information and ensure that it is handled in compliance with privacy regulations."
    },
    {
      question: "How can I schedule an appointment with a healthcare provider?",
      answer: "On our platform, you can easily schedule appointments with healthcare providers. Simply browse through the available providers, select a convenient time slot, and book your appointment online."
    },
    {
      question: "What types of conditions can be treated through telemedicine?",
      answer: "Telemedicine can address a wide range of non-emergency medical conditions. It is suitable for conditions like cold and flu symptoms, allergies, minor injuries, skin rashes, mental health concerns, and routine follow-up consultations."
    },
    {
      question: "How do I access my medical records online?",
      answer: "Our platform allows you to securely access and manage your medical records online. Once you create an account, you can view your test results, prescriptions, appointment history, and other relevant medical information."
    },
    {
      question: "Can I get a prescription through telemedicine?",
      answer: "Yes, if deemed appropriate by the healthcare provider during your telemedicine consultation, they can issue prescriptions electronically to your preferred pharmacy."
    },
    {
      question: "What if I require a physical examination or diagnostic tests?",
      answer: "In cases where a physical examination or diagnostic tests are necessary, the healthcare provider may recommend visiting a nearby clinic or laboratory. They can guide you through the process and provide necessary referrals."
    },
    {
      question: "Are telemedicine services covered by insurance?",
      answer: "Many insurance providers now cover telemedicine services. It's advisable to check with your insurance provider to understand your coverage and reimbursement options."
    }
  ];
  

function FAQ() {

    return (
        <div className="mb-4" >
            <h1 className="text-4xl font-bold text-c-blue-950 m-4">
              Frequently asked questions
            </h1>
            <div className="max-h-80 overflow-x-scroll p-2" >
                {faqs.map(faq => {
                    return <Question 
                        answer={faq.answer} 
                        question={faq.question} 
                    />
                })}
            </div>
        </div>
    );
}

function Question({ question,answer }) {

    const [isOpen,setIsOpen] = useState(false);

    return (
        <div className="border border-black p-2 mb-4" >
            <div className="flex text-xl r-sm:text-base justify-between" >
                <p>{question}</p>
                <button className="w-6 aspect-square" 
                    onClick={() => setIsOpen(prev => !prev)}
                >
                    <img className="w-full aspect-square"
                        src={isOpen ? up : down}
                        alt=""
                    />
                </button>
            </div>
            {isOpen ? <p>{answer}</p> : null}
        </div>
    )
}

export default FAQ;