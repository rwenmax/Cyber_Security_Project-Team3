variable "var_region_name_tf" {
  default = "eu-west-1"
}

#Name of private key
variable "var_global_key_name_tf" {
    default = "cyber-10x-group3"
}

#Location of the private key
variable "var_private_key_loc_tf" {
  default = "/home/vagrant/.ssh/cyber-10x-group3.pem"
}

locals {
  vpc_id = "${aws_vpc.java10x_cyberg3_vpc_tf.id}"
}


####Change thissssss###########
variable "var_ami_database_server_tf"{
  default = "ami-08ca3fed11864d6bb"
}


variable "var_ami_app_server_tf" {
  default = "ami-08ca3fed11864d6bb"
}










