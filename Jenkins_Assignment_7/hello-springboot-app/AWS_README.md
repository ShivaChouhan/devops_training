# AWS EKS Cluster Setup (Manual Configuration)

This guide walks you through creating an Amazon EKS cluster without EKS Auto Mode and manually adding worker nodes using the AWS Console.

![Configuration Options](image.png)

## Prerequisites
- AWS account with IAM permissions for EKS
- AWS CLI configured with proper credentials
- `eksctl` installed (recommended)
- kubectl installed

---

## Step 1: Create EKS Cluster (Without Auto Mode)

1. Navigate to the Amazon EKS service in AWS Console
2. Click "Add cluster" → "Create"
3. Select "Custom configuration" (not Quick configuration with EKS Auto Mode)
4. Under "EKS Auto Mode" section, choose to **not** use EKS Auto Mode
5. Configure basic cluster settings:
   - **Cluster name**: Enter a unique name (e.g., `my-manual-cluster`)
   - **Kubernetes version**: Select your preferred version
   - **Cluster service role**: Create or select an IAM role with EKS permissions
6. Configure networking (VPC, subnets, security groups)
7. Leave logging options as default or customize as needed
8. Click "Create" and wait for cluster creation (typically 10-15 minutes)

**Cluster Creation Screen Example:**  
![Cluster Creation](Images_and_Videos/Cluster_creation.png)

---

## Step 2: Add Worker Nodes from Compute Tab

1. Navigate to your EKS cluster in AWS Console
2. Select the **"Compute"** tab in the cluster dashboard
3. Click **"Add node group"** button
4. Configure the node group settings:
   - **Node group name**: Enter a descriptive name (e.g., `prod-worker-nodes`)
   - **Node IAM role**: Select an existing role or create new one with:
     - `AmazonEKSWorkerNodePolicy`
     - `AmazonEC2ContainerRegistryReadOnly`
     - `AmazonEKS_CNI_Policy`
   - **Compute configuration**:
     - AMI type: `Amazon Linux 2` (recommended)
     - Capacity type: On-Demand or Spot
     - Instance type: Select appropriate size (e.g., `t3.medium` for dev, `m5.large` for prod)
   - **Node group scaling configuration**:
     - Desired size: Initial number of nodes (e.g., 2)
     - Minimum size: Minimum nodes for scaling (e.g., 2)
     - Maximum size: Maximum nodes allowed (e.g., 5)
5. **Network configuration**:
   - Select the same VPC used by your cluster
   - Choose subnets across multiple Availability Zones for high availability
6. **Review and create**:
   - Verify all settings
   - Click "Create" to provision the nodes
7. Wait 5-10 minutes for nodes to register with the cluster

**Cluster Nodes/Compute Tab Example:**  
![Cluster Nodes](Images_and_Videos/Cluster_Nodes.png)

---

## Verify Node Connection
After creation:
```bash
kubectl get nodes --watch
```

---

## Step 3: Verify Cluster Access

1. Update your kubeconfig:
   ```bash
   aws eks update-kubeconfig --name my-manual-cluster --region us-west-2
   ```
2. Test access:
   ```bash
   kubectl get nodes
   ```

