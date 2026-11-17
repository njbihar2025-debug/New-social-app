package com.example.data

import com.example.data.model.AdPlan
import com.example.data.model.BankBranchItem
import com.example.data.model.BusinessItem
import com.example.data.model.DigitalServiceItem
import com.example.data.model.GovtJobItem
import com.example.data.model.HelplineItem
import com.example.data.model.JobSectionType
import com.example.data.model.NewsItem
import com.example.data.model.PromotionOfferItem

object SupaulSeedData {

    val supaulBlocks = listOf(
        "सभी प्रखंड",
        "सुपौल सदर",
        "त्रिवेणीगंज",
        "निर्मली",
        "वीरपुर",
        "पिपरा",
        "छातापुर",
        "राघोपुर",
        "सरायगढ़-भपटियाही",
        "किशनपुर",
        "मरौना",
        "बसंतपुर"
    )

    val newsItems = listOf(
        NewsItem(
            id = "news_1",
            title = "सुपौल-अररिया रेल लाइन व कोसी क्षेत्र के यात्रियों के लिए नई समय-सारिणी पर चर्चा",
            summary = "सुपौल जंक्शन और सरायगढ़-निर्मली रूट पर सुबह की पैसेंजर ट्रेनों के ठहराव व समय सुधार को लेकर जनप्रतिनिधियों की बैठक संपन्न।",
            fullContent = "सुपौल जिला मुख्यालय स्थित रेलवे स्टेशन और सरायगढ़-निर्मली-झंझारपुर रेल खंड पर दैनिक यात्रियों, छात्रों और व्यापारियों की सुविधा के लिए अतिरिक्त फेरे और बेहतर कनेक्टिविटी पर जोर दिया गया है। व्यापार मंडल ने सुबह के समय दरभंगा-सहरसा दिशा की गाड़ियों के समय समन्वय की मांग रखी, जिससे मंडी और कॉलेज जाने वालों को सीधा लाभ मिलेगा।",
            blockName = "सुपौल सदर",
            topicTag = "विकास व यातायात",
            timeAgo = "25 मिनट पहले",
            isBreaking = true
        ),
        NewsItem(
            id = "news_2",
            title = "त्रिवेणीगंज मंडी में धान और मक्का खरीद केंद्रों पर पारदर्शी तौल की नई व्यवस्था",
            summary = "प्रखंड के पैक्स गोदामों पर नमी मापक यंत्र और टोकन प्रणाली लागू, किसानों को 48 घंटे में डीबीटी भुगतान का लक्ष्य।",
            fullContent = "त्रिवेणीगंज अनुमंडल क्षेत्र के किसानों को न्यूनतम समर्थन मूल्य (MSP) का पूरा लाभ दिलाने के लिए प्रखंड सहकारिता पदाधिकारी ने सभी सक्रिय पैक्स केंद्रों का निरीक्षण किया। किसानों से अपील की गई है कि वे आधार और जमीन रसीद के साथ ऑनलाइन निबंधन अवश्य करा लें ताकि बिना किसी बिचौलिए के सीधे बैंक खाते में राशि प्राप्त हो सके।",
            blockName = "त्रिवेणीगंज",
            topicTag = "मंडी व कृषि",
            timeAgo = "2 घंटे पहले",
            isBreaking = false
        ),
        NewsItem(
            id = "news_3",
            title = "वीरपुर कोसी बराज और अनुमंडल अस्पताल में आधुनिक डिजिटल जांच सुविधा शुरू",
            summary = "बसंतपुर व वीरपुर के नागरिकों को अब एक्स-रे और बेसिक पैथोलॉजी रिपोर्ट के लिए बाहर नहीं जाना होगा।",
            fullContent = "वीरपुर अनुमंडल अस्पताल परिसर में मरीजों की बढ़ती संख्या को देखते हुए डिजिटल रिपोर्टिंग काउंटर और हेल्प डेस्क प्रारंभ किया गया है। आयुष्मान भारत कार्ड धारकों के लिए अलग पंजीकरण विंडो उपलब्ध कराई गई है जिससे बुजुर्गों और गर्भवती महिलाओं को कतार से राहत मिली है।",
            blockName = "वीरपुर",
            topicTag = "स्वास्थ्य व प्रशासन",
            timeAgo = "4 घंटे पहले",
            isBreaking = false
        ),
        NewsItem(
            id = "news_4",
            title = "निर्मली व मरौना प्रखंड में डिजिटल भूमि अभिलेख शिविर का आयोजन इस सप्ताह",
            summary = "परिमार्जन प्लस और दाखिल-खारिज के लंबित आवेदनों के त्वरित निपटारे के लिए अंचल स्तर पर विशेष कैंप लगेगा।",
            fullContent = "निर्मली और मरौना अंचल कार्यालयों में रैयतों की सुविधा के लिए हल्का कर्मचारियों की टीम पंचायत भवन स्तर पर दस्तावेजों का सत्यापन करेगी। आवेदक अपने पुराने लगान रसीद, केवाला और आधार प्रति के साथ निर्धारित तिथि को सुबह 10 बजे से शाम 4 बजे तक शिविर में भाग ले सकते हैं।",
            blockName = "निर्मली",
            topicTag = "प्रशासन सूचना",
            timeAgo = "6 घंटे पहले",
            isBreaking = false
        ),
        NewsItem(
            id = "news_5",
            title = "पिपरा व छातापुर के स्कूलों में छात्रवृत्ति और अपार (APAAR) आईडी अपडेट अभियान",
            summary = "कक्षा 9 से 12 के विद्यार्थियों के लिए आधार-बैंक सीडिंग और छात्रवृत्ति फॉर्म सत्यापन की अंतिम तिथि नजदीक।",
            fullContent = "जिला शिक्षा कार्यालय की ओर से पिपरा, छातापुर और किशनपुर प्रखंड के सभी उच्च माध्यमिक विद्यालयों को निर्देश जारी किया गया है कि छात्रों का मेधासॉफ्ट (MedhaSoft) डेटा त्रुटिरहित अपलोड करें। अभिभावकों से अनुरोध है कि बच्चे के बैंक खाते में NPCI/DBT लिंक की स्थिति नजदीकी बैंक या CSC केंद्र से जांच करा लें।",
            blockName = "पिपरा",
            topicTag = "शिक्षा अपडेट",
            timeAgo = "कल",
            isBreaking = false
        ),
        NewsItem(
            id = "news_6",
            title = "सुपौल महावीर चौक व स्टेशन रोड पर शाम को यातायात सुगम बनाने हेतु ई-रिक्शा रूट तय",
            summary = "नगर परिषद और यातायात पुलिस ने मुख्य बाजार में जाम से निजात के लिए वन-वे व स्टैंड जोन चिह्नित किए।",
            fullContent = "महावीर चौक, लोहिया नगर चौक और स्टेशन रोड के प्रमुख व्यापारिक क्षेत्रों में खरीदारी करने आने वाले नागरिकों की सुविधा के लिए ई-रिक्शा और ऑटो पड़ाव का सीमांकन किया गया है। व्यापारियों और राहगीरों ने इस पहल का स्वागत किया है।",
            blockName = "सुपौल सदर",
            topicTag = "शहर अपडेट",
            timeAgo = "कल",
            isBreaking = false
        )
    )

    val digitalServices = listOf(
        DigitalServiceItem(
            id = "srv_1",
            title = "जाति, आवासीय व आय प्रमाण पत्र (RTPS)",
            subtitle = "ServicePlus Bihar ऑनलाइन आवेदन और डाउनलोड",
            category = "सरकारी प्रमाण पत्र",
            requiredDocs = listOf("आधार कार्ड", "पासपोर्ट साइज फोटो", "स्व-घोषणा पत्र", "राशन कार्ड/वोटर आईडी"),
            processingTime = "10 कार्य दिवस",
            govtFee = "निःशुल्क (ऑनलाइन)",
            officialUrl = "https://serviceonline.bihar.gov.in/",
            stepsSummary = "ServicePlus पोर्टल पर 'लोक सेवाओं का अधिकार' चुनें -> सामान्य प्रशासन विभाग से प्रमाण पत्र प्रकार चुनें -> ओटीपी सत्यापन के बाद आवेदन सबमिट करें।"
        ),
        DigitalServiceItem(
            id = "srv_2",
            title = "बिहार भूमि: लगान रसीद, दाखिल-खारिज व परिमार्जन",
            subtitle = "जमीन का खाता-खेसरा, जमाबंदी पंजी और ऑनलाइन लगान",
            category = "राजस्व एवं भूमि",
            requiredDocs = listOf("जमाबंदी संख्या / खाता-खेसरा", "रजिस्ट्री डीड (केवाला)", "पिछली लगान रसीद", "मोबाइल नंबर"),
            processingTime = "15 से 35 कार्य दिवस (दाखिल-खारिज)",
            govtFee = "सरकारी लगान अनुसार",
            officialUrl = "https://biharbhumi.bihar.gov.in/",
            stepsSummary = "Bihar Bhumi पोर्टल पर अपनी जमाबंदी देखें -> त्रुटि सुधार हेतु 'परिमार्जन प्लस' या नए क्रय हेतु 'ऑनलाइन दाखिल-खारिज' विकल्प का उपयोग करें।"
        ),
        DigitalServiceItem(
            id = "srv_3",
            title = "आधार कार्ड अपडेट, PVC कार्ड व पैन लिंक",
            subtitle = "UIDAI अपॉइंटमेंट, स्टेटस चेक और e-Aadhaar डाउनलोड",
            category = "पहचान व KYC",
            requiredDocs = listOf("रजिस्टर्ड मोबाइल नंबर", "पते का प्रमाण (बिजली बिल/निवास)", "जन्म प्रमाण पत्र (यदि सुधार हो)"),
            processingTime = "5 से 15 दिन",
            govtFee = "₹50 (डेमोग्राफिक/PVC), ₹100 (बायोमेट्रिक)",
            officialUrl = "https://myaadhaar.uidai.gov.in/",
            stepsSummary = "myAadhaar पोर्टल पर OTP से लॉगिन करें या नजदीकी सुपौल आधार सेवा केंद्र / बैंक शाखा में अपॉइंटमेंट बुक करें।"
        ),
        DigitalServiceItem(
            id = "srv_4",
            title = "आयुष्मान भारत कार्ड (PM-JAY) व राशन कार्ड",
            subtitle = "₹5 लाख तक मुफ्त इलाज कार्ड और e-KYC अपडेट",
            category = "स्वास्थ्य व खाद्य सुरक्षा",
            requiredDocs = listOf("राशन कार्ड नंबर", "सभी सदस्यों का आधार कार्ड", "आधार लिंक्ड मोबाइल नंबर"),
            processingTime = "तुरंत (e-KYC स्वीकृति के बाद)",
            govtFee = "CSC / सरकारी केंद्र पर निःशुल्क",
            officialUrl = "https://beneficiary.nha.gov.in/",
            stepsSummary = "Beneficiary NHA पोर्टल पर मोबाइल OTP से लॉगिन करें -> जिला 'Supaul' चुनें -> परिवार के सदस्यों का आधार e-KYC पूरा कर कार्ड डाउनलोड करें।"
        ),
        DigitalServiceItem(
            id = "srv_5",
            title = "NBPDCL बिजली बिल भुगतान व नया कनेक्शन",
            subtitle = "उत्तरी बिहार बिजली बोर्ड स्मार्ट मीटर रिचार्ज व शिकायत",
            category = "बिल व यूटिलिटी",
            requiredDocs = listOf("उपभोक्ता संख्या (Consumer ID)", "UPI / बैंक खाता", "नए कनेक्शन हेतु आवास प्रमाण"),
            processingTime = "तुरंत रसीद",
            govtFee = "बिल राशि अनुसार",
            officialUrl = "https://nbpdcl.co.in/",
            stepsSummary = "NBPDCL वेबसाइट या Bihar Bijli Smart Meter ऐप पर अपना CA नंबर दर्ज कर बिल देखें और सुरक्षित UPI भुगतान करें।"
        ),
        DigitalServiceItem(
            id = "srv_6",
            title = "पीएम किसान सम्मान निधि (PM-KISAN) e-KYC",
            subtitle = "किस्त की स्थिति, DBT लिंकिंग और नया किसान पंजीकरण",
            category = "किसान सेवाएं",
            requiredDocs = listOf("किसान पंजीकरण संख्या", "आधार कार्ड", "NPCI लिंक्ड बैंक पासबुक", "भूमि विवरण"),
            processingTime = "सत्यापन उपरांत अगली किस्त",
            govtFee = "निःशुल्क",
            officialUrl = "https://pmkisan.gov.in/",
            stepsSummary = "PM-Kisan पोर्टल पर 'Know Your Status' से किस्त जांचें और नजदीकी वसुधा केंद्र (CSC) से बायोमेट्रिक e-KYC कराएं।"
        )
    )

    val bankBranches = listOf(
        BankBranchItem(
            id = "bank_1",
            bankName = "भारतीय स्टेट बैंक (SBI)",
            branchName = "सुपौल मुख्य शाखा (Main Branch)",
            ifscCode = "SBIN0000188",
            address = "स्टेशन रोड, महावीर चौक के पास, सुपौल - 852131",
            blockName = "सुपौल सदर",
            timings = "सुबह 10:00 - शाम 4:00 (रविवार व 2nd/4th शनिवार बंद)",
            phone = "18001234",
            hasAtm = true
        ),
        BankBranchItem(
            id = "bank_2",
            bankName = "पंजाब नेशनल बैंक (PNB)",
            branchName = "त्रिवेणीगंज बाजार शाखा",
            ifscCode = "PUNB0145200",
            address = "मेन रोड, दुर्गा मंदिर चौक, त्रिवेणीगंज - 852139",
            blockName = "त्रिवेणीगंज",
            timings = "सुबह 10:00 - शाम 4:00",
            phone = "18001800",
            hasAtm = true
        ),
        BankBranchItem(
            id = "bank_3",
            bankName = "उत्तर बिहार ग्रामीण बैंक (UBGB)",
            branchName = "सुपौल क्षेत्रीय कार्यालय व मुख्य शाखा",
            ifscCode = "CBIN0R10001",
            address = "लोहिया नगर, कलेक्ट्रेट रोड, सुपौल - 852131",
            blockName = "सुपौल सदर",
            timings = "सुबह 10:00 - शाम 4:00",
            phone = "06473224510",
            hasAtm = false
        ),
        BankBranchItem(
            id = "bank_4",
            bankName = "बैंक ऑफ बड़ौदा (Bank of Baroda)",
            branchName = "निर्मली शाखा",
            ifscCode = "BARB0NIRMAL",
            address = "स्टेशन चौक, निर्मली, जिला सुपौल - 847452",
            blockName = "निर्मली",
            timings = "सुबह 10:00 - शाम 4:00",
            phone = "18005700",
            hasAtm = true
        ),
        BankBranchItem(
            id = "bank_5",
            bankName = "इंडिया पोस्ट पेमेंट्स बैंक (IPPB / डाकघर)",
            branchName = "सुपौल मुख्य डाकघर (Head Post Office)",
            ifscCode = "IPOS0000001",
            address = "डाकघर चौक, सुपौल - 852131",
            blockName = "सुपौल सदर",
            timings = "सुबह 9:30 - शाम 5:00 (आधार व सुकन्या समृद्धि सेवा)",
            phone = "155299",
            hasAtm = false
        )
    )

    val govtJobs = listOf(
        GovtJobItem(
            id = "job_1",
            title = "बिहार पुलिस सिपाही एवं चालक संवर्ग भर्ती 2026",
            department = "CSBC बिहार (केंद्रीय चयन पर्षद)",
            sectionType = JobSectionType.LATEST_JOB,
            qualification = "12वीं पास",
            postsOrStatus = "2,800+ पद",
            keyDateLabel = "अंतिम तिथि: 10 दिसम्बर 2026",
            isUrgent = true,
            eligibilityDetails = "मान्यता प्राप्त बोर्ड से इंटरमीडिएट (10+2) उत्तीर्ण एवं शारीरिक दक्षता मानदंड पूर्ण होना आवश्यक।",
            ageLimit = "18 से 25 वर्ष (आरक्षित वर्ग को बिहार सरकार के नियमानुसार छूट)",
            applicationFee = "सामान्य/BC/EBC: ₹675 | SC/ST/महिला: ₹180",
            officialUrl = "https://csbc.bihar.gov.in/"
        ),
        GovtJobItem(
            id = "job_2",
            title = "BSSC इंटर स्तरीय (द्वितीय) एवं कार्यालय परिचारी भर्ती",
            department = "बिहार कर्मचारी चयन आयोग (BSSC)",
            sectionType = JobSectionType.LATEST_JOB,
            qualification = "10वीं / 12वीं",
            postsOrStatus = "विभिन्न लिपिकीय पद",
            keyDateLabel = "आवेदन सक्रिय (ऑनलाइन)",
            isUrgent = false,
            eligibilityDetails = "किसी भी मान्यता प्राप्त संस्थान से 10वीं या 12वीं उत्तीर्ण; लिपिक पद हेतु बेसिक कंप्यूटर ज्ञान व टाइपिंग वांछनीय।",
            ageLimit = "18 से 37 वर्ष (सामान्य पुरुष), 40 वर्ष (महिला/BC/EBC)",
            applicationFee = "₹540 (सामान्य/पिछड़ा वर्ग) | ₹135 (SC/ST/दिव्यांग)",
            officialUrl = "https://bssc.bihar.gov.in/"
        ),
        GovtJobItem(
            id = "job_3",
            title = "भारतीय डाक (India Post) ग्रामीण डाक सेवक (GDS) बिहार सर्कल",
            department = "भारतीय डाक विभाग (सुपौल डिवीजन सहित)",
            sectionType = JobSectionType.LATEST_JOB,
            qualification = "10वीं पास",
            postsOrStatus = "मेरिट आधारित चयन (बिना परीक्षा)",
            keyDateLabel = "अंतिम तिथि: 28 नवम्बर 2026",
            isUrgent = true,
            eligibilityDetails = "10वीं कक्षा में गणित और अंग्रेजी विषय के साथ उत्तीर्ण तथा स्थानीय भाषा (हिंदी) व साइकिल चलाने का ज्ञान।",
            ageLimit = "18 से 40 वर्ष",
            applicationFee = "₹100 (UR/OBC/EWS पुरुष) | महिला व SC/ST: निःशुल्क",
            officialUrl = "https://indiapostgdsonline.gov.in/"
        ),
        GovtJobItem(
            id = "job_4",
            title = "BPSC TRE शिक्षक भर्ती व जिला संवर्ग मेरिट लिस्ट अपडेट",
            department = "बिहार लोक सेवा आयोग (BPSC)",
            sectionType = JobSectionType.RESULT,
            qualification = "स्नातक / B.Ed / D.El.Ed",
            postsOrStatus = "जिला आवंटन व रिजल्ट प्रकाशित",
            keyDateLabel = "परिणाम जारी • PDF देखें",
            isUrgent = true,
            eligibilityDetails = "सुपौल सहित सभी जिलों के लिए विषयवार कट-ऑफ अंक व चयनित अभ्यर्थियों की रोल नंबर सूची आयोग पोर्टल पर उपलब्ध है।",
            ageLimit = "आयोग अधिसूचना अनुसार",
            applicationFee = "लागू नहीं (रिजल्ट जांच निःशुल्क)",
            officialUrl = "https://bpsc.bihar.gov.in/"
        ),
        GovtJobItem(
            id = "job_5",
            title = "बिहार सिविल कोर्ट क्लर्क एवं प्यून परीक्षा परिणाम",
            department = "जिला एवं सत्र न्यायालय भर्ती समिति, पटना",
            sectionType = JobSectionType.RESULT,
            qualification = "10वीं / स्नातक",
            postsOrStatus = "अंतिम चयन सूची जारी",
            keyDateLabel = "रिजल्ट उपलब्ध",
            isUrgent = false,
            eligibilityDetails = "उम्मीदवार अपना रजिस्ट्रेशन नंबर और जन्मतिथि डालकर स्कोरकार्ड व साक्षात्कार के अंक देख सकते हैं।",
            ageLimit = "लागू नहीं",
            applicationFee = "निःशुल्क",
            officialUrl = "https://districts.ecourts.gov.in/patna"
        ),
        GovtJobItem(
            id = "job_6",
            title = "SSC GD कॉन्स्टेबल एवं रेलवे RRB तकनीशियन प्रवेश पत्र",
            department = "कर्मचारी चयन आयोग / रेलवे भर्ती बोर्ड",
            sectionType = JobSectionType.ADMIT_CARD,
            qualification = "10वीं / ITI / डिप्लोमा",
            postsOrStatus = "परीक्षा शहर व हॉल टिकट जारी",
            keyDateLabel = "परीक्षा तिथि से 4 दिन पूर्व डाउनलोड",
            isUrgent = true,
            eligibilityDetails = "परीक्षा केंद्र पर प्रवेश पत्र के साथ मूल फोटो पहचान पत्र (आधार कार्ड/पैन कार्ड) और 2 पासपोर्ट फोटो ले जाना अनिवार्य है।",
            ageLimit = "आधिकारिक एडमिट कार्ड निर्देश देखें",
            applicationFee = "निःशुल्क डाउनलोड",
            officialUrl = "https://ssc.gov.in/"
        ),
        GovtJobItem(
            id = "job_7",
            title = "बिहार विद्यालय परीक्षा समिति (BSEB) D.El.Ed / STET एडमिट कार्ड",
            department = "BSEB पटना",
            sectionType = JobSectionType.ADMIT_CARD,
            qualification = "12वीं / स्नातक",
            postsOrStatus = "डमी व अंतिम प्रवेश पत्र लिंक",
            keyDateLabel = "डाउनलोड सक्रिय",
            isUrgent = false,
            eligibilityDetails = "अभ्यर्थी आवेदन संख्या और जन्मतिथि के माध्यम से प्रवेश पत्र डाउनलोड कर परीक्षा केंद्र व रिपोर्टिंग समय जांचें।",
            ageLimit = "लागू नहीं",
            applicationFee = "निःशुल्क",
            officialUrl = "https://secondary.biharboardonline.com/"
        )
    )

    val initialBusinesses = listOf(
        BusinessItem(
            id = "biz_1",
            name = "कोसी लाइफलाइन हॉस्पिटल एवं ट्रॉमा सेंटर",
            category = "स्वास्थ्य / अस्पताल",
            ownerName = "डॉ. राजीव झा",
            phone = "06473224810",
            address = "गौरवगढ़ चौक, मुख्य मार्ग, सुपौल",
            blockName = "सुपौल सदर",
            timings = "24 घंटे आपातकालीन सेवा",
            rating = "4.7",
            isVerified = true,
            description = "24x7 इमरजेंसी, सामान्य शल्य चिकित्सा, डिजिटल एक्स-रे, पैथोलॉजी और मातृत्व देखभाल।"
        ),
        BusinessItem(
            id = "biz_2",
            name = "मिथिला डिजिटल वसुधा केंद्र एवं ऑनलाइन पॉइंट",
            category = "डिजिटल व CSC केंद्र",
            ownerName = "अमित कुमार मंडल",
            phone = "9835012340",
            address = "महावीर चौक, पोस्ट ऑफिस गली, सुपौल",
            blockName = "सुपौल सदर",
            timings = "सुबह 8:30 - शाम 7:30",
            rating = "4.8",
            isVerified = true,
            description = "जाति-आवास-आय प्रमाण पत्र, पैन कार्ड, रेलवे/फ्लाइट टिकट, मनी ट्रांसफर और नौकरी फॉर्म आवेदन।"
        ),
        BusinessItem(
            id = "biz_3",
            name = "संकल्प कोचिंग एवं लाइब्रेरी हब",
            category = "शिक्षा / कोचिंग",
            ownerName = "प्रो. संजय यादव",
            phone = "9934208811",
            address = "चिल्ड्रेन पार्क रोड, वार्ड नं. 12, सुपौल",
            blockName = "सुपौल सदर",
            timings = "सुबह 6:00 - रात 9:00",
            rating = "4.6",
            isVerified = true,
            description = "BPSC, SSC, रेलवे और बोर्ड परीक्षाओं की तैयारी; शांत वाई-फाई युक्त सेल्फ-स्टडी लाइब्रेरी।"
        ),
        BusinessItem(
            id = "biz_4",
            name = "अन्नपूर्णा फैमिली रेस्टोरेंट एवं स्वीट्स",
            category = "होटल / रेस्टोरेंट",
            ownerName = "मनीष अग्रवाल",
            phone = "9431077654",
            address = "मेन रोड, बंसी चौक के पास, त्रिवेणीगंज",
            blockName = "त्रिवेणीगंज",
            timings = "सुबह 8:00 - रात 10:30",
            rating = "4.5",
            isVerified = true,
            description = "शुद्ध शाकाहारी थाली, ताज़ी मिठाइयां, समोसा-चाट और पारिवारिक पार्टी बुकिंग सुविधा।"
        ),
        BusinessItem(
            id = "biz_5",
            name = "माँ दुर्गा कृषि एवं हार्डवेयर मार्ट",
            category = "किराना / कृषि मार्ट",
            ownerName = "रमेश मेहता",
            phone = "9708451122",
            address = "स्टेशन रोड, निर्मली बाजार",
            blockName = "निर्मली",
            timings = "सुबह 7:30 - शाम 8:00",
            rating = "4.4",
            isVerified = true,
            description = "प्रमाणित बीज, खाद, सिंचाई पाइप, पंप सेट और घरेलू निर्माण सामग्री उचित मूल्य पर।"
        ),
        BusinessItem(
            id = "biz_6",
            name = "कोसी मोबाइल केयर एवं इलेक्ट्रॉनिक्स",
            category = "इलेक्ट्रॉनिक्स / मोबाइल",
            ownerName = "फैजान आलम",
            phone = "9122334455",
            address = "गोल चौक, वीरपुर बाजार",
            blockName = "वीरपुर",
            timings = "सुबह 9:30 - शाम 8:30",
            rating = "4.6",
            isVerified = true,
            description = "सभी कंपनियों के स्मार्टफोन, तुरंत डिस्प्ले रिपेयर, लैपटॉप सेवा और आसान EMI सुविधा।"
        )
    )

    val promotionOffers = listOf(
        PromotionOfferItem(
            id = "promo_1",
            businessName = "अन्नपूर्णा फैमिली रेस्टोरेंट",
            title = "फैमिली थाली और मिठाई कॉम्बो पर विशेष छूट",
            discountBadge = "15% OFF",
            description = "शनिवार-रविवार को परिवार के साथ डिनर या 2 किलो से अधिक मिठाई ऑर्डर पर तुरंत 15% की विशेष बचत।",
            validTill = "30 नवम्बर 2026 तक",
            blockName = "त्रिवेणीगंज",
            phone = "9431077654"
        ),
        PromotionOfferItem(
            id = "promo_2",
            businessName = "संकल्प कोचिंग एवं लाइब्रेरी हब",
            title = "नए बैच और लाइब्रेरी मेंबरशिप पर निःशुल्क डेमो सप्ताह",
            discountBadge = "FREE 7 DAYS",
            description = "BSSC व बिहार पुलिस बैच में प्रवेश लेने वाले छात्रों को 7 दिन की फ्री लाइब्रेरी सीट और स्टडी मटेरियल।",
            validTill = "सीमित सीटें उपलब्ध",
            blockName = "सुपौल सदर",
            phone = "9934208811"
        ),
        PromotionOfferItem(
            id = "promo_3",
            businessName = "कोसी मोबाइल केयर, वीरपुर",
            title = "स्मार्टफोन स्क्रीन रिप्लेसमेंट के साथ टेम्पर्ड ग्लास मुफ्त",
            discountBadge = "FREE GIFT",
            description = "किसी भी स्मार्टफोन की स्क्रीन या बैटरी बदलने पर प्रीमियम एज-टू-एज ग्लास व चेकअप बिल्कुल मुफ्त।",
            validTill = "इस सप्ताह विशेष",
            blockName = "वीरपुर",
            phone = "9122334455"
        )
    )

    val adPlans = listOf(
        AdPlan(
            id = "plan_basic",
            title = "लोकल बूस्ट पैक",
            duration = "7 दिन",
            price = "₹299",
            features = listOf(
                "बिजनेस डायरेक्टरी में टॉप लिस्टिंग",
                "वेरिफाइड बैज और डायरेक्ट कॉल बटन",
                "प्रखंड-आधारित सर्च में प्राथमिकता"
            ),
            isPopular = false
        ),
        AdPlan(
            id = "plan_featured",
            title = "सुपौल होम बैनर पैक",
            duration = "15 दिन",
            price = "₹599",
            features = listOf(
                "होम स्क्रीन व प्रमोशन पेज पर फीचर्ड कार्ड",
                "डिस्काउंट ऑफर बैज (जैसे 20% OFF)",
                "सभी 11 प्रखंडों के यूजर्स तक सीधी पहुंच"
            ),
            isPopular = true
        ),
        AdPlan(
            id = "plan_monthly",
            title = "मासिक प्रीमियम पार्टनर",
            duration = "30 दिन",
            price = "₹999",
            features = listOf(
                "पूरे महीने बैनर + डायरेक्टरी टॉप पोजिशन",
                "फेस्टिवल और सेल अलर्ट प्रमोशन",
                "व्हाट्सएप/कॉल एनालिटिक्स सपोर्ट"
            ),
            isPopular = false
        )
    )

    val helplines = listOf(
        HelplineItem("help_1", "आपातकालीन पुलिस / मदद", "112", "24x7 त्वरित सहायता"),
        HelplineItem("help_2", "एम्बुलेंस सेवा", "102", "निःशुल्क रोगी वाहन"),
        HelplineItem("help_3", "अग्निशमन (फायर)", "101", "आग व बचाव कार्य"),
        HelplineItem("help_4", "महिला हेल्पलाइन", "181", "सुरक्षा एवं परामर्श"),
        HelplineItem("help_5", "सुपौल जिला नियंत्रण कक्ष", "06473224001", "प्रशासनिक सहायता")
    )
}
