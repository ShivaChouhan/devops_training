### Introduction

**Challenges in IT Infrastructure**

Infrastructure as code (IaC) tools allow you to manage infrastructure with configuration files rather than through a graphical user interface. IaC allows you to build, change, and manage your infrastructure in a safe, consistent, and repeatable way by defining resource configurations you can version, reuse, and share.

---

## Terraform

### Installation

Terraform is HashiCorp's infrastructure as a code tool. It lets you define resources and infrastructure in human-readable, declarative configuration files and manages your infrastructure's lifecycle. Using Terraform has several advantages over manually managing your infrastructure:

- Terraform can manage infrastructure on multiple cloud platforms.
- The human-readable configuration language helps you write infrastructure code quickly.
- Terraform's state allows you to track resource changes throughout your deployments.
- You can commit your configurations to version control to safely collaborate on infrastructure.

---

### Linux Installation

```bash
sudo apt-get update && sudo apt-get install -y gnupg software-properties-common
wget -O- https://apt.releases.hashicorp.com/gpg | \
  gpg --no-default-keyring \
  --keyring /usr/share/keyrings/hashicorp-archive-keyring.gpg \
  --fingerprint \
  | gpg --dearmor | \
  sudo tee /usr/share/keyrings/hashicorp-archive-keyring.gpg

echo "deb [signed-by=/usr/share/keyrings/hashicorp-archive-keyring.gpg] \
https://apt.releases.hashicorp.com $(lsb_release -cs) main" | \
  sudo tee /etc/apt/sources.list.d/hashicorp.list

sudo apt update
sudo apt-get install terraform
```

---

## HCL

### Blocks and Arguments

HashiCorp Configuration Language, also used by other HashiCorp products.

Example:
```hcl
filename = "/home/ubuntu/abc123.txt"
```

### Syntax Constructs

- **Block:** Container for other content.
- **Argument:** Assigns a value to a name.

```hcl
resource "<provider>_<type>" "<name>" {
  argument1 = ""
  argument2 = ""
}
```

Example:

```hcl
resource "random_string" "rand-str" {
  length = 16
  special = true
  override_special = "!#$%&*()-_=+[]{}<>:?"
}

output "rand-str" {
  value = random_string.rand-str[*].result
}
```
---

## command to create public and private key which is used in ec2 instance

```hcl
ssh-keygen
```
> And after that I provide a file name in which i want to store the key like:terra-key
---

## Terraform commands

- `terraform init` – installs required providers
- `terraform validate` – for validating .tf file (optional)
- `terraform plan` – creates execution plan
- `terraform apply` – executes infrastructure changes
- `terraform destroy` – destroy infrastructure changes
- `terraform apply -auto-approve` – apply infrastructure without approval
- `terraform destroy -auto-approve` – destroy infrastructure without approval
- `terraform taint` - for destroying any resource forcefully and recreate it 
                      Ex:terraform taint aws_instance.my_ec2  : After applying this command when we execute terraform apply then terraform specifically destroy that resource and recreate it.
- `terraform refresh` - >If we manually apply changes in cloud resources out of terraform like from Aws console we change
                        instance state or stop the instance.
                        >Than after executing refresh command its simply update the terraform state file to match the
                        actual cloud state of resources
---
