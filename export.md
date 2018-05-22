COPY ckm_t_pilot.golden_clusters_by_customer (
    cluster_id, cluster_size, confidence_level, 
    golden_company_name, golden_display_name, 
    golden_company_reg_no, golden_customer_name,
    golden_customer_type,
    golden_dob, golden_firstname, 
    golden_surname,
    cd_si_ext, 
    src_customer_id, customer_type, firstname, surname, 
    company_reg_no, company_name, customer_type, dob)
TO '/tmp/golden.csv' 
WITH HEADER = TRUE AND MAXOUTPUTSIZE=200;


SELECT 
    cluster_id, cluster_size, confidence_level, 
    golden_company_name, golden_display_name, 
    golden_company_reg_no, golden_customer_name,
    golden_customer_type,
    golden_dob, golden_firstname, 
    golden_surname,
    cd_si_ext, 
    src_customer_id, customer_type, firstname, surname, company_reg_no, company_name, customer_type, dob
FROM  ckm_t_pilot.golden_clusters_by_customer
LIMIT 100;

SELECT sys_apl, agreement_start_date, maturity_date, terminated_date, src_customer_id, agreement_id, contract_type, vin_no, status_id
FROM ckm_t_pilot.contracts_by_status
LIMIT 100;

COPY ckm_t_pilot.contracts_by_status (sys_apl, agreement_start_date, maturity_date, terminated_date, 
src_customer_id, agreement_id, contract_type, vin_no, status_id)
TO '/tmp/contrats.csv' 
WITH HEADER = TRUE AND MAXOUTPUTSIZE=100;


