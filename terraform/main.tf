provider "aws" {
    region = "${var.var_region_name_tf}"
}

#Creating a VPC
resource "aws_vpc" "java10x_cyberg3_vpc_tf" {
    cidr_block = "10.113.0.0/16"

    enable_dns_support = true
    enable_dns_hostnames = true

    tags = {
        Name = "java10x_cyberg3_vpc"
    }
}

#DNS Routing 
resource "aws_route53_zone" "java10x_cyberg3_r53_zone_tf" {
  name = "cyber_group3"

  vpc {
    vpc_id = "${local.vpc_id}"
  }
  tags = {
    Name = "java10x_cyberg3_r53_zone"
  }
}

#Internet gateway
resource "aws_internet_gateway" "java10x_cyberg3_igw_tf" {
  vpc_id = "${local.vpc_id}"

  tags = {
    Name = " java10x_cyberg3_igw"
  }
}

#Route Table - gives access to public network
resource "aws_route_table" "java10x_cyberg3_rt_tf" {
  vpc_id = "${local.vpc_id}"
  route {
    cidr_block = "0.0.0.0/0"
    gateway_id = aws_internet_gateway.java10x_cyberg3_igw_tf.id
  }
  tags =  {
    Name = "java10x_cyberg3_rt"
  }
}

#Subnets Creations 
module "subnets_module" {
  source = "./modules/subnets"
  var_vpc_id_tf = "${local.vpc_id}"
}



module "database_module" {
  source = ""
  
}

module "application_module" {
  
}

module "proxy_module" {

}


module "bastion_module" {

}


#App Instance/Security Group

