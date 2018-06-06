//Configures the data loader to create the schema
config create_schema: false, load_new: true

golden   = File.csv('/Users/cedricklunven/Downloads/c360/data/golden.csv').delimiter(',')
contrats = File.csv('/Users/cedricklunven/Downloads/c360/data/contrats.csv').delimiter(',')

// Chargement des CLUSTER
load(golden).asVertices {
    label 'cluster'
    key 'cluster_id'
    ignore 'cd_si_ext'
    ignore 'src_customer_id'
    ignore 'customer_type'
    ignore 'firstname'
    ignore 'surname'
    ignore 'company_reg_no'
    ignore 'company_name'
    ignore 'customer_type'
    ignore 'dob'
}

// Chargement des CUSTOMERS
load(golden).asVertices {
    label 'customer'
    key 'src_customer_id'
    ignore 'cluster_id'
    ignore 'cluster_size'
    ignore 'confidence_level'
    ignore 'golden_company_name'
    ignore 'golden_display_name'
    ignore 'golden_company_reg_no'
    ignore 'golden_customer_name'
    ignore 'golden_customer_type'
    ignore 'golden_dob'
    ignore 'golden_firstname'
    ignore 'golden_surname'
}

// Chargement des CONTRATS
load(contrats).asVertices {
    label 'contract'
    key 'agreement_id'
}

// Chargement des VEHICULES
load(contrats).asVertices {
    label 'vehicule'
    key 'vin_no'
    ignore 'sys_apl'
    ignore 'agreement_start_date'
    ignore 'maturity_date'
    ignore 'terminated_date'
    ignore 'src_customer_id'
    ignore 'agreement_id'
    ignore 'status_id'
    ignore 'contract_type'
}

// Links Cluster -> Customer
load(golden).asEdges {
    label 'containsCustomer'
    outV 'cluster_id', {
        label 'cluster'
        key 'cluster_id'
    }
    inV 'src_customer_id', {
        label 'customer'
        key 'src_customer_id'
    }
    ignore 'cluster_size'
    ignore 'confidence_level'
    ignore 'golden_company_name'
    ignore 'golden_display_name'
    ignore 'golden_company_reg_no'
    ignore 'golden_customer_name'
    ignore 'golden_customer_type'
    ignore 'golden_dob'
    ignore 'golden_firstname'
    ignore 'golden_surname'
    ignore 'cd_si_ext'
    ignore 'src_customer_id'
    ignore 'customer_type'
    ignore 'firstname'
    ignore 'surname'
    ignore 'company_reg_no'
    ignore 'company_name'
    ignore 'customer_type'
    ignore 'dob'
}

// Links Contrat -> Customer
load(contrats).asEdges {
    label 'suscriptedBy'
    outV 'agreement_id', {
        label 'contract'
        key 'agreement_id'
    }
    inV 'src_customer_id', {
        label 'customer'
        key 'src_customer_id'
    }
    ignore 'vin_no'
    ignore 'sys_apl'
    ignore 'agreement_start_date'
    ignore 'maturity_date'
    ignore 'terminated_date'
    ignore 'src_customer_id'
    ignore 'agreement_id'
    ignore 'status_id'
    ignore 'contract_type'
}

// Links Contrat -> Vehicule
load(contrats).asEdges {
    label 'subjectOfContract'
    outV 'agreement_id', {
        label 'contract'
        key 'agreement_id'
    }
    inV 'vin_no', {
        label 'vehicule'
        key 'vin_no'
    }
    ignore 'sys_apl'
    ignore 'agreement_start_date'
    ignore 'maturity_date'
    ignore 'terminated_date'
    ignore 'src_customer_id'
    ignore 'agreement_id'
    ignore 'status_id'
    ignore 'contract_type'
}

