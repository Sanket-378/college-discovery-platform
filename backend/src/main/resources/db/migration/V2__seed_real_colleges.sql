-- Replace the temporary Phase 12 smoke-test records with a realistic demo catalog.
DELETE FROM colleges
WHERE slug IN ('phase12-test-engineering', 'phase12-test-science');

INSERT INTO colleges (
    name, slug, description, city, state, country, address, website_url,
    ownership_type, college_type, established_year, accreditation,
    nirf_rank, overall_rating, review_count
) VALUES
('College of Engineering Pune (COEP Technological University)', 'coep-technological-university-pune', 'A public technological university with undergraduate and postgraduate engineering programmes.', 'Pune', 'Maharashtra', 'India', 'Wellesley Road, Shivajinagar, Pune, Maharashtra 411005', 'https://www.coeptech.ac.in', 'PUBLIC', 'Engineering', 1854, 'NAAC', NULL, 4.4, 0),
('Veermata Jijabai Technological Institute (VJTI)', 'vjti-mumbai', 'An autonomous public engineering institute offering undergraduate, postgraduate and doctoral programmes.', 'Mumbai', 'Maharashtra', 'India', 'H. R. Mahajani Marg, Matunga East, Mumbai, Maharashtra 400019', 'https://vjti.ac.in', 'PUBLIC', 'Engineering', 1887, 'NAAC', NULL, 4.3, 0),
('Visvesvaraya National Institute of Technology', 'vnit-nagpur', 'A public technical institute offering engineering, architecture and applied science programmes.', 'Nagpur', 'Maharashtra', 'India', 'South Ambazari Road, Nagpur, Maharashtra 440010', 'https://vnit.ac.in', 'PUBLIC', 'Engineering', 1960, 'NAAC', NULL, 4.3, 0),
('Walchand College of Engineering', 'walchand-college-of-engineering-sangli', 'An autonomous engineering college with undergraduate, postgraduate and research programmes.', 'Sangli', 'Maharashtra', 'India', 'Vishrambag, Sangli, Maharashtra 416415', 'https://www.walchandsangli.ac.in', 'PUBLIC', 'Engineering', 1947, 'NAAC', NULL, 4.2, 0),
('Shri Guru Gobind Singhji Institute of Engineering and Technology', 'sggs-institute-nanded', 'An autonomous public engineering institute with a broad range of technical programmes.', 'Nanded', 'Maharashtra', 'India', 'Vishnupuri, Nanded, Maharashtra 431606', 'https://www.sggs.ac.in', 'PUBLIC', 'Engineering', 1981, 'NAAC', NULL, 4.1, 0),
('Indian Institute of Technology Bombay', 'iit-bombay', 'A premier public research institute offering engineering, science, design and management programmes.', 'Mumbai', 'Maharashtra', 'India', 'Powai, Mumbai, Maharashtra 400076', 'https://www.iitb.ac.in', 'PUBLIC', 'Engineering', 1958, 'NAAC', NULL, 4.7, 0),
('Indian Institute of Technology Delhi', 'iit-delhi', 'A public research institute with programmes in engineering, technology, science and management.', 'New Delhi', 'Delhi', 'India', 'Hauz Khas, New Delhi, Delhi 110016', 'https://home.iitd.ac.in', 'PUBLIC', 'Engineering', 1961, 'NAAC', NULL, 4.7, 0),
('Indian Institute of Technology Madras', 'iit-madras', 'A public technical and research institute with programmes spanning engineering, science and humanities.', 'Chennai', 'Tamil Nadu', 'India', 'Sardar Patel Road, Chennai, Tamil Nadu 600036', 'https://www.iitm.ac.in', 'PUBLIC', 'Engineering', 1959, 'NAAC', NULL, 4.8, 0),
('National Institute of Technology Tiruchirappalli', 'nit-tiruchirappalli', 'A public technical university offering undergraduate, postgraduate and doctoral education.', 'Tiruchirappalli', 'Tamil Nadu', 'India', 'Tanjore Main Road, Tiruchirappalli, Tamil Nadu 620015', 'https://www.nitt.edu', 'PUBLIC', 'Engineering', 1964, 'NAAC', NULL, 4.4, 0),
('Indian Institute of Technology Hyderabad', 'iit-hyderabad', 'A public research institute offering technology, science, design and liberal arts programmes.', 'Hyderabad', 'Telangana', 'India', 'Kandi, Sangareddy, Telangana 502284', 'https://iith.ac.in', 'PUBLIC', 'Engineering', 2008, 'NAAC', NULL, 4.4, 0),
('Indian Institute of Science', 'indian-institute-of-science-bengaluru', 'A public institute dedicated to advanced research and higher education in science and engineering.', 'Bengaluru', 'Karnataka', 'India', 'CV Raman Avenue, Bengaluru, Karnataka 560012', 'https://iisc.ac.in', 'PUBLIC', 'Science', 1909, 'NAAC', NULL, 4.7, 0),
('Birla Institute of Technology and Science, Pilani', 'bits-pilani', 'A private deemed university known for higher education and research in science, engineering and technology.', 'Pilani', 'Rajasthan', 'India', 'Vidya Vihar, Pilani, Rajasthan 333031', 'https://www.bits-pilani.ac.in', 'DEEMED', 'Engineering', 1964, 'NAAC', NULL, 4.5, 0),
('Indian Institute of Technology Kanpur', 'iit-kanpur', 'A public research institute offering education and research across engineering, science and management.', 'Kanpur', 'Uttar Pradesh', 'India', 'Kalyanpur, Kanpur, Uttar Pradesh 208016', 'https://www.iitk.ac.in', 'PUBLIC', 'Engineering', 1959, 'NAAC', NULL, 4.7, 0),
('Indian Institute of Technology Kharagpur', 'iit-kharagpur', 'A public technical institute with comprehensive programmes in engineering, science, architecture and management.', 'Kharagpur', 'West Bengal', 'India', 'Kharagpur, West Bengal 721302', 'https://www.iitkgp.ac.in', 'PUBLIC', 'Engineering', 1951, 'NAAC', NULL, 4.6, 0),
('National Institute of Technology Karnataka, Surathkal', 'nitk-surathkal', 'A public technical institute offering undergraduate, postgraduate and doctoral programmes.', 'Mangaluru', 'Karnataka', 'India', 'Srinivasnagar, Surathkal, Mangaluru, Karnataka 575025', 'https://www.nitk.ac.in', 'PUBLIC', 'Engineering', 1960, 'NAAC', NULL, 4.4, 0);

INSERT INTO courses (
    college_id, name, degree_level, specialization, duration_years,
    total_fees, currency, seats, eligibility, description
)
SELECT id, 'Bachelor of Technology', 'UG', 'Computer Science and Engineering', 4.0,
       200000, 'INR', 60, '10+2 with Physics and Mathematics',
       'Undergraduate engineering programme in computer science and related technologies.'
FROM colleges
WHERE slug IN (
    'coep-technological-university-pune', 'vjti-mumbai', 'vnit-nagpur',
    'walchand-college-of-engineering-sangli', 'sggs-institute-nanded',
    'iit-bombay', 'iit-delhi', 'iit-madras', 'nit-tiruchirappalli',
    'iit-hyderabad', 'bits-pilani', 'iit-kanpur', 'iit-kharagpur', 'nitk-surathkal'
);

INSERT INTO courses (
    college_id, name, degree_level, specialization, duration_years,
    total_fees, currency, seats, eligibility, description
)
SELECT id, 'Bachelor of Science', 'UG', 'Physics', 3.0,
       120000, 'INR', 40, '10+2 with Physics and Mathematics',
       'Undergraduate science programme with a foundation in physics and mathematical methods.'
FROM colleges
WHERE slug = 'indian-institute-of-science-bengaluru';
